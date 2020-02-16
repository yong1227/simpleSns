package com.simple.sns.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.sns.controller.PostController;
import com.simple.sns.domain.FeedVO;
import com.simple.sns.domain.FollowVO;
import com.simple.sns.repository.FeedDAO;
import com.simple.sns.repository.FollowDAO;

@Service
public class FeedService {
	
	static org.slf4j.Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	PostService postService;
	
	@Autowired
	FeedDAO feedDAO;
	
	@Autowired
	FollowDAO followDAO;

	public void insertFeedByPostIdAndUserId(Long id, Long userId) {
		FeedVO feedVO = new FeedVO();
		feedVO.setPostId(id);
		feedVO.setFolloweeId(userId);
		feedVO.setUserId(userId);
		
		try {
			// insertFeedMySelf
			// already data
			feedDAO.insertFeed(feedVO);
		} catch (Exception e) {
			logger.info("이미 있어요");
		}
		
		// insertFeedByFollowee
		// findFollower
		
		List<FollowVO> followVOs;
		try {
			// none follower
			followVOs = followDAO.findFollowVOsByFolloweeId(userId);
			for (FollowVO followVO : followVOs) {
				feedVO.setPostId(id);
				feedVO.setFolloweeId(userId);
				feedVO.setUserId(followVO.getFollowerId());
				feedDAO.insertFeed(feedVO);
			}
		} catch (Exception e) {
			logger.info("follower가 없어요");
		} 
	}
}
