package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.InvitedUserDao;
import com.ebupt.justholdon.server.database.entity.InvitedUser;

@Service("invitedUserService")
@Transactional
public class InvitedUserServiceImpl implements InvitedUserService {

	@Autowired
	UserService userService;
	@Autowired
	InvitedUserDao invitedUserDao;
	@Override
	public Integer save(InvitedUser newInstance) {
		return invitedUserDao.save(newInstance);
	}

	@Override
	public InvitedUser get(Integer id) {
		return invitedUserDao.get(id);
	}

	@Override
	public void update(InvitedUser transientObject) {
		invitedUserDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return invitedUserDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		invitedUserDao.delete(id);
	}

	@Override
	public List<InvitedUser> findAll() {
		return invitedUserDao.findAll();
	}

	@Override
	public void saveOrUpdate(InvitedUser transientObject) {
		invitedUserDao.saveOrUpdate(transientObject);
	}

	Criterion[] countRestric(Date start,Date end,Long sponsorId){
		return new Criterion[] {
				Restrictions.between("createTime", start, end),
				Restrictions.eq("sponsorId", sponsorId)
		};
	}
	Criterion[] getUserRestric(String invitedUserName){
		return new Criterion[]{
				Restrictions.eq("invitedUserName", invitedUserName)
		};
	}
	@Override
	public Integer countInvitedUserNums(Date startDate, Date endDate,
			Long sponsorId) {
		ProjectionList projList = Projections.projectionList().add(Projections.count("invitedUserName"));
		List<?> result =  invitedUserDao.findByCriteria(projList,countRestric(startDate,endDate,sponsorId));
		return Integer.valueOf(Utils.convertResults(result).get(0).toString());
	}

	
	@Override
	public void inviteUser(Long sponsorId, String invitedUserName) {
		if(hasInvited(invitedUserName)) return;
		String userName = "";
		if(sponsorId.equals(1L)){
			userName = "系统";
		}
		else
		{
			userName = userService.get(sponsorId).getUserName();
		}
		InvitedUser item = new InvitedUser()
								.setInvitedUserName(invitedUserName)
								.setSponsorId(sponsorId)
								.setSponsorName(userName);
		invitedUserDao.save(item);
	}

	@Override
	public boolean hasInvited(String userName) {
		List<InvitedUser> result = invitedUserDao.findByCriteria(getUserRestric(userName));
		return !(result==null||result.isEmpty());
	}

	@Override
	public List<String> hasInvitedUser() {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("invitedUserName"));
		List<?> results = invitedUserDao.findByCriteria(projectionList,new Criterion[]{});
		return Utils.convertResults(results);
	}

}
