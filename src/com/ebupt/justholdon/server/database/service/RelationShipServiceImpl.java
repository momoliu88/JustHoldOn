package com.ebupt.justholdon.server.database.service;

//import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.RelationShipDao;
import com.ebupt.justholdon.server.database.entity.RelationShip;
import com.ebupt.justholdon.server.database.entity.User;

@Service("relationShipService")
@Transactional
public class RelationShipServiceImpl implements RelationShipService{
	@Autowired
	private RelationShipDao relationShipDao;
	@Autowired
	private UserService userService;
	@Override
	public Integer save(RelationShip newInstance) {
		return relationShipDao.save(newInstance);
	}

	@Override
	public RelationShip get(Integer id) {
		return relationShipDao.get(id);
	}

	@Override
	public void update(RelationShip transientObject) {
		relationShipDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return relationShipDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		RelationShip relation = get(id);
		relation.getUserA().getRelationShips().remove(relation);
		relation.setUserA(null).setUserB(null);
		relationShipDao.delete(id);
	}

	@Override
	public List<RelationShip> findAll() {
		return relationShipDao.findAll();
	}

	@Override
	public void saveOrUpdate(RelationShip transientObject) {
		relationShipDao.saveOrUpdate(transientObject);		
	}
	private Criterion[] checkFriendCriterion(Long uidA,Long uidB){
		Criterion [] crits = {
				Restrictions.or(
					Restrictions.and(
						Restrictions.eq("userA",userService.get(uidA)),
						Restrictions.eq("userB",userService.get(uidB))
						),
						Restrictions.and(
								Restrictions.eq("userA",userService.get(uidB)),
								Restrictions.eq("userB",userService.get(uidA))
						)
					)
								
		};
		return crits;
	}
	private Criterion[] findFriendCriterion(Long uidA){
		Criterion [] crits = {
				Restrictions.eq("userA",userService.get(uidA)),
		};
		return crits;
	}
	private Criterion[] findFriendCriterion(Long uidA,String key){
		Criterion [] crits = {
				Restrictions.eq("userA",userService.get(uidA)),
				Restrictions.ilike("userBName", "%"+key+"%")
		};
		return crits;
	}
	private boolean checkValid(User userA,User userB){
		if(null == userA || null == userB){
			return false;
		}
		return true;
	}
	private void createRelationShip(User user1,User user2){
		RelationShip relationShip = new RelationShip();
		relationShip.setUserA(user1).setUserAName(user1.getUserName())
					.setUserB(user2).setUserBName(user2.getUserName());
		relationShipDao.save(relationShip);
	}
	@Override
	public boolean beFriend(Long id1, Long id2) {
		relationShipDao.setOrder(Arrays.asList(Order.desc("createTime")));
		List<RelationShip> relationShips = relationShipDao.findByCriteria(checkFriendCriterion(id1,id2));
		User userA = userService.get(id1);
		User userB = userService.get(id2);
		if(!checkValid(userA,userB))
		{
			relationShipDao.setOrder(null);
			return false;
		}
		if(null == relationShips || relationShips.isEmpty())
		//add relationship
		{
			createRelationShip(userA,userB);
			createRelationShip(userB,userA);
		}		
		relationShipDao.setOrder(null);
		return true;
	}
	private void delete(RelationShip relationShip){
		User user = relationShip.getUserA();
		user.setFriendNums(user.getFriendNums()-1);
		userService.update(user);
		delete(relationShip.getId());
	}
	@Override
	public boolean removeFriend(Long id1, Long id2) {
		relationShipDao.setOrder(Arrays.asList(Order.desc("createTime")));
		List<RelationShip> relationShips = relationShipDao.findByCriteria(checkFriendCriterion(id1,id2));
		User userA = userService.get(id1);
		User userB = userService.get(id2);
		if(!checkValid(userA,userB))
		{
			relationShipDao.setOrder(null);
			return false;
		}
		if(null != relationShips && relationShips.size() == 2)
		{
			delete(relationShips.get(0));
			delete(relationShips.get(1));
		}
		relationShipDao.setOrder(null);
		return true;
	}
	
	@Override
	public Integer countFriends(Long uid) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.groupProperty("userA")).add(Projections.count("userA"));
		List<?> ret = relationShipDao.findByCriteria(projectionList,findFriendCriterion(uid));
		if(null == ret || ret.isEmpty()) return 0;
		return Integer.valueOf(((Object[])ret.get(0))[1].toString());
	}

	@Override
	public boolean isFriend(Long uidA, Long uidB) {
		List<RelationShip> relationShips = relationShipDao.findByCriteria(checkFriendCriterion(uidA,uidB));	
		return (relationShips!=null)&&(relationShips.size()>0);
	}
	private List<?> warpCriterions(ProjectionList projectionList,Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return relationShipDao.findByCriteria(projectionList,length,crits);
		RelationShip relationShip = get(startId);
		return relationShipDao.findByCriteria(projectionList,length,
				Utils.warpIdRangeLimit(relationShip.getCreateTime(),"createTime",after, crits));
	}
	@Override
	public List<User> findFriends(Long uid, Integer startId, Integer length,
			boolean after) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("userB"));
//		List<RelationShip> results = relationShipDao.
//		findByCriteria(projectionList,length,Utils.warpIdRangeLimit(after, startId,
//				findFriendCriterion(uid)));
		List<?> results = warpCriterions(projectionList,length,startId,after,findFriendCriterion(uid));
		return Utils.convertResults(results);
	}
	
	@Override
	public List<User> findFriends(Long uid) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("userB"));
		List<?> results = relationShipDao.findByCriteria(projectionList,findFriendCriterion(uid));
		return Utils.convertResults(results);
	}

	@Override
	public List<User> searchFriend(Long uid, String key) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("userB"));
		return Utils.convertResults(relationShipDao.findByCriteria(projectionList,findFriendCriterion(uid,key)));
	}

	@Override
	public List<User> searchFriend(Long uid, String key, Integer startId,
			Integer length, boolean after) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("userB"));
		return Utils.convertResults(
//	relationShipDao.findByCriteria(projectionList,length,Utils.warpIdRangeLimit(after, startId,findFriendCriterion(uid,key)))
			warpCriterions(projectionList,length,startId,after,findFriendCriterion(uid,key))
				);
	}

}
