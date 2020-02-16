package com.simple.sns.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.simple.sns.domain.FollowVO;
import com.simple.sns.domain.PostAndUserVO;
import com.simple.sns.domain.PostVO;
import com.simple.sns.domain.ResponseResult;
import com.simple.sns.domain.TokenVO;
import com.simple.sns.repository.FollowDAO;
import com.simple.sns.repository.PostDAO;
import com.simple.sns.repository.UserDAO;

@Service
public class PostService {
	static org.slf4j.Logger logger = LoggerFactory.getLogger(PostService.class);

	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private FollowDAO followDAO;
	
	public int insertPostByTitleAndContent(PostVO postVO) {
		return postDAO.insertPostByTitleAndContent(postVO);
	}
	
	public PostVO findPostById(Long id) {
		return postDAO.findPostById(id);
	}
	
	public List<PostAndUserVO> findPostsAndUserWithIsFollow(String accesstoken){
		TokenVO tokenVO =  userDAO.findTokenByToken(accesstoken);
		Long userId = tokenVO.getUserId();
		
		List<PostAndUserVO> findPostsAndUserWithIsFollows = postDAO.findPostsAndUserWithIsFollow(userId);
		
		for (PostAndUserVO findPostsAndUserVO : findPostsAndUserWithIsFollows) {
			int countFolloweeId = followDAO.findCountFollowVOsByFollowerId(userId);
			if(countFolloweeId == 0) {
				findPostsAndUserVO.getUser().setIsFollow(false);
			}else {
				if(findPostsAndUserVO.getUserId() == userId){
					findPostsAndUserVO.getUser().setIsFollow(null);
				}else {
					List<FollowVO> followVOs =  followDAO.findFollowVOsByFollowerId(userId);
					
					for (FollowVO followVO : followVOs) {
						Long followeeIdByFollowVO = followVO.getFolloweeId();
						Long followeeIdByPostAndUserVO =  findPostsAndUserVO.getUserId();
						
						if (followeeIdByPostAndUserVO == followeeIdByFollowVO) {
							findPostsAndUserVO.getUser().setIsFollow(true);
							break;
						} else {
							findPostsAndUserVO.getUser().setIsFollow(false);
						}
					}
				}
			}	
		}
		
		return findPostsAndUserWithIsFollows;
	}

	public List<PostAndUserVO> findPostsAndUser() {
		return postDAO.findPostsAndUser();
	}
	
	public List<PostAndUserVO> findPostAndUserByUserId(Long userId){
		return postDAO.findPostAndUserByUserId(userId);
	}
	
	public List<PostAndUserVO> findPostAndUserByToken(String token){
		return postDAO.findPostAndUserByToken(token);
	}
	
	public PostAndUserVO findPostAndUserByPostId(Long postId) {
		return postDAO.findPostAndUserByPostId(postId);
	}

	public void deletePostByPostId(Long postId) {
		postDAO.deletePostByPostId(postId);
	}

	public int updatePostTitleAndContent(PostVO postVO) {
		return postDAO.updatePostTitleAndContent(postVO);
	}

	public ResponseResult findMyPostAndUserAndMyFollowerByUserId(String accesstoken) {
		TokenVO tokenVO = userDAO.findTokenByToken(accesstoken);
		Long userId = tokenVO.getUserId();
		List<PostAndUserVO> postAndUserVOs = postDAO.findMyPostAndUserAndMyFollowerByUserId(userId);
		
		for (PostAndUserVO postAndUserVO : postAndUserVOs) {
			if(postAndUserVO.getUserId() == userId ) {
				postAndUserVO.getUser().setIsFollow(null);
				break;
			}else {
				List<FollowVO> followVOs =  followDAO.findFollowVOsByFollowerId(userId);
				for (FollowVO followVO : followVOs) {
					if (postAndUserVO.getUserId() ==  followVO.getFolloweeId()) {
						postAndUserVO.getUser().setIsFollow(true);
						break;
					}else {
						postAndUserVO.getUser().setIsFollow(false);
					}
				}
			}
		}
		return new ResponseResult(HttpStatus.OK.value(), "Success", postAndUserVOs ); 
	}
}
