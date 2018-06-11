package com.hfy.service.impl;

import com.hfy.entity.*;
import com.hfy.mapper.*;
import com.hfy.pulgin.pagehelper.Condition;
import com.hfy.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("activityServiceImpl")
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private NewsActivityMapper newsActivityMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private CommentUserMapper commnetUserMapper;

    @Autowired
    public void setBaseMapper(ActivityMapper activityMapper) {
        super.setBaseMapper(activityMapper);
    }

    @Override
    public List<Activity> getIsSelectByActivity() {
        return activityMapper.getIsSelectByActivity();
    }

    @Override
    public List<CommentUser> findByTargetIdType(Condition condition) {
        return commnetUserMapper.findByTargetIdType(condition);
    }

    @Override
    public List<ActivityUserLinks> queryParticipant(Condition condition) {
        return activityMapper.queryParticipant(condition);
    }

    @Override
    public List<ActivityUserLinks> queryActivityVote(Condition condition) {
        return activityMapper.queryActivityVote(condition);
    }

    @Override
    public List<ActivityUserLinks> findVoteCountByActivityId(Long id) {
        return activityMapper.findVoteCountByActivityId(id);
    }

    @Override
    public List<ActivityUserLinks> findParticipantByActivityId(Long id) {
        return activityMapper.findParticipantByActivityId(id);
    }

    @Override
    public void deleteByuserActivityId(Map<String, Long> userActivityId) {
        activityMapper.deleteByuserActivityId(userActivityId);
    }

    @Override
    public List<ActivityUserLinks> participantDetail(Map<String, String> query) {
        return activityMapper.participantDetail(query);
    }

    @Override
    public Activity findByID(Long id) throws Exception {
        Activity activity = activityMapper.findByID(id);
        Map<String, String> map = new HashMap<String, String>();
        map.put("property", "activityId");
        map.put("value", id + "");
        List<Topic> topics = topicMapper.getListByProperty(map);
        if (topics != null && topics.size() > 0) {
            for (Topic topic : topics) {
                topic.setOptions(optionMapper.findByTopicId(topic.getId()));
            }
            activity.setTopics(topics);
        }
        return activity;
    }

    @Override
    public List<StatiExcel> getStatiExcelUser(Long activityId) {
        return activityMapper.getStatiExcelUser(activityId);
    }

    @Override
    public List<StatiExcel> getStatiExcelTitle(Long activityId) {
        return activityMapper.getStatiExcelTitle(activityId);
    }

    @Override
    public List<StatiExcel> getStatiExcelOption(Long activityId) {
        return activityMapper.getStatiExcelOption(activityId);
    }

    @Override
    public List<StatiExcel> getStatiExcelQue(Long activityId) {
        return activityMapper.getStatiExcelQue(activityId);
    }

    @Override
    public void deleteActivityVote(Map<String, Long> userActivityId) {
        activityMapper.deleteActivityVote(userActivityId);
    }

    @Override
    public List<AnswerDetail> selectAnswerDetail(Map<String, Long> userActivityId) {
        return activityMapper.selectAnswerDetail(userActivityId);
    }

    @Override
    public void deleteAnswerDetail(Map<String, Long> userActivityId) {
        activityMapper.deleteAnswerDetail(userActivityId);
    }

    @Override
    public List<DepartmentUser> findUserAccomplish(Long activityId) {
        return activityMapper.findUserAccomplish(activityId);
    }

    @Override
    public void updateScore(Map<String, String> query) {
        activityMapper.updateScore(query);
    }

    @Override
    public List<ActView> getActView(Condition condition) {
        return activityMapper.getActView(condition);
    }

    @Override
    public List<ActivityView> getViews(Condition condition) {
        return activityMapper.getViews(condition);
    }

    @Override
    public Integer getTotalByIds(Long[] ids) {
        return activityMapper.getTotalByIds(ids);
    }

    @Override
    public void deleteUserExam(Map<String, Long> userActivityId) {
        activityMapper.deleteUserExam(userActivityId);
    }

    @Override
    public List<Activity> queryVoteCount(Map<String, Object> map) {
        return activityMapper.queryVoteCount(map);
    }

    @Override
    public List<Activity> queryViewCount(Map<String, Object> map) {
        return activityMapper.queryViewCount(map);
    }

    @Override
    public List<Activity> queryAllViewCount(Map<String, Object> map) {
        return activityMapper.queryAllViewCount(map);
    }

    @Override
    public List<Activity> queryAllVoteCount(Map<String, Object> map) {
        return activityMapper.queryAllVoteCount(map);
    }

    @Override
    public void addNewsActivity(Activity activity) throws Exception {
        if (activity.getStatus() == 1 && activity.getActType().getId() != 8) {
            NewsActivity newsActivity = new NewsActivity();
            newsActivity.setObjectId(activity.getId());
            newsActivity.setObjectType(2);
            newsActivity.setType(activity.getActType().getId().intValue());
            newsActivity.setTitle(activity.getName());
            newsActivity.setTitleImage(activity.getTitleImage());
            newsActivity.setDigest(activity.getDigest());
            newsActivity.setGroups(activity.getGroupView());
            newsActivity.setDepartment(activity.getDepView());
            newsActivity.setPviews(activity.getReviewCount());
            newsActivity.setIsComment(activity.getIsComment());
            newsActivity.setIsShare(activity.getIsShare());
            newsActivity.setCommentCount(activity.getCommentCount());
            newsActivity.setParticipant(activity.getParticipant());
            newsActivity.setAddress(activity.getAddress());
            newsActivity.setEndTime(activity.getEndTime());
            newsActivity.setCreateDate(activity.getCreateDate());
            newsActivity.setUpdateDate(activity.getCreateDate());
            newsActivityMapper.add(newsActivity);
        }
    }

    @Override
    public void updateNewsActivity(Activity activity) throws Exception {
        activity = activityMapper.findByID(activity.getId());
        if (activity.getStatus() == 1 && activity.getActType().getId() != 8) {
            if (activity.getMenuID() != null && activity.getMenuID() == 1 && !activity.getIsHomePage()) {
                newsActivityMapper.deleteByActivity(new Long[] {activity.getId()});
                return;
            }
            NewsActivity newsActivity = new NewsActivity();
            newsActivity.setObjectId(activity.getId());
            newsActivity.setObjectType(2);
            newsActivity.setType(activity.getActType().getId().intValue());
            newsActivity.setTitle(activity.getName());
            newsActivity.setTitleImage(activity.getTitleImage());
            newsActivity.setDigest(activity.getDigest());
            newsActivity.setGroups(activity.getGroupView());
            newsActivity.setDepartment(activity.getDepView());
            newsActivity.setPviews(activity.getReviewCount());
            newsActivity.setIsComment(activity.getIsComment());
            newsActivity.setIsShare(activity.getIsShare());
            newsActivity.setCommentCount(activity.getCommentCount());
            newsActivity.setParticipant(activity.getParticipant());
            newsActivity.setAddress(activity.getAddress());
            newsActivity.setEndTime(activity.getEndTime());
            newsActivity.setCreateDate(activity.getCreateDate());
            newsActivity.setUpdateDate(activity.getUpdateDate());

            Condition condition = new Condition();
            condition.getSearchMap().put("objectId", activity.getId() + "");
            condition.getSearchMap().put("objectType", "2");
            List<NewsActivity> newsActivities = newsActivityMapper.query(condition);
            if (newsActivities.size() > 0) {
                newsActivityMapper.update(newsActivity);
            } else {
                newsActivityMapper.add(newsActivity);
            }
        } else {
            newsActivityMapper.deleteByActivity(new Long[] {activity.getId()});
        }
    }

    @Override
    public void deleteNewsActivity(Long[] ids) throws Exception {
        newsActivityMapper.deleteByActivity(ids);
    }

    @Override
    public List<OrderExcel> orderDetailToExcel(Condition condition) throws Exception {
        return activityMapper.orderDetailToExcel(condition);
    }

    @Override
    public List<ActivityExcel> exportActivity(Condition condition) throws Exception {
        return activityMapper.exportActivity(condition);
    }

    @Override
    public List<Activity> queryAct(Condition condition) {
        return activityMapper.queryAct(condition);
    }

    @Override
    public Boolean activityIsTop(Long id) {
        return activityMapper.findActivityIsTop(id);
    }

    @Override
    public List<Activity> oneKeyExportActivity(Condition condition) {
        return activityMapper.oneKeyExportActivity(condition);
    }

    @Override
    public void updateNewsActivityNew(Activity activity, Boolean flag, Integer menuId) throws Exception {
        activity = activityMapper.findByID(activity.getId());
        if (activity.getStatus() == 1 && activity.getActType().getId() != 8) {
            NewsActivity newsActivity = new NewsActivity();
            newsActivity.setObjectId(activity.getId());
            newsActivity.setObjectType(2);
            newsActivity.setType(activity.getActType().getId().intValue());
            newsActivity.setTitle(activity.getName());
            newsActivity.setTitleImage(activity.getTitleImage());
            newsActivity.setDigest(activity.getDigest());
            newsActivity.setGroups(activity.getGroupView());
            newsActivity.setDepartment(activity.getDepView());
            newsActivity.setPviews(activity.getReviewCount());
            newsActivity.setIsComment(activity.getIsComment());
            newsActivity.setIsShare(activity.getIsShare());
            newsActivity.setCommentCount(activity.getCommentCount());
            newsActivity.setParticipant(activity.getParticipant());
            newsActivity.setAddress(activity.getAddress());
            newsActivity.setEndTime(activity.getEndTime());
            newsActivity.setCreateDate(activity.getCreateDate());
            newsActivity.setUpdateDate(activity.getUpdateDate());
            newsActivity.setMenuId(menuId);
            newsActivity.setIsHomePage(flag);

            Condition condition = new Condition();
            condition.getSearchMap().put("objectId", activity.getId() + "");
            condition.getSearchMap().put("objectType", "2");
            List<NewsActivity> newsActivities = newsActivityMapper.query(condition);
            if (newsActivities.size() > 0) {
                newsActivityMapper.update(newsActivity);
            } else {
                newsActivityMapper.add(newsActivity);
            }
        }
    }

    @Override
    public void updateCategoryId(Activity activity) {
        activityMapper.updateCategoryId(activity);
    }

    @Override
    public void updateOrderType(Activity activity) {
        activityMapper.updateOrderType(activity);
    }
    
    @Override
    public List<Activity> queryActivity(String typeId) throws Exception {
        return activityMapper.queryActivity(typeId);
    }
    
    @Override
    public String queryGroups(String activityId) throws Exception {
        return activityMapper.queryGroups(activityId);
    }
}
