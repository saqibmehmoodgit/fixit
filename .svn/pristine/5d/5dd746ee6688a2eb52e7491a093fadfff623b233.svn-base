package com.fixit.dao;

import java.util.List;

import org.skyway.spring.util.dao.JpaDao;

import com.fixit.domain.vo.Blog;
import com.fixit.utility.FixitException;

public interface BlogDao extends JpaDao<Blog> {

	/**
	 * Method used get list of blog object filtered with status
	 * 
	 * @param Status
	 *            - Status(pending/published/approved/decline) of a blog.
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return List of Blog object accorind to status.
	 */
	public List<Blog> findBlogDataByStatus(String Status, int min, int max);

	/**
	 * method used to update status by admin.
	 * 
	 * @param blogStatus
	 *            - Status(pending/published/approved/decline) of a blog.
	 * @param blogId
	 *            - blogId of a particular blog and is of Integer type.
	 * @return 1 on success
	 */
	public int updateStatusByBlogId(String blogStatus, int blogId);

	/**
	 * used to get a list of all blog .
	 * 
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * 
	 */
	public List<Blog> findAllBlog(int min, int max);

	/**
	 * used to get a list of all blog object of a particular user from session.
	 * 
	 * @param userId
	 *            - userId of User from session.
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return List(collection) of object of blog of a particular user
	 */
	public List<Blog> findAllBlogByUserId(int userId, int min, int max);

	/**
	 * @param blogId
	 *            - blogId of a particular blog.
	 * @return Blog object of a particular blog
	 */
	public Blog findAllBlogByBlogId(int blogId);

	/**
	 * this method used to fetch blog with userId by status for admin.
	 * 
	 * @param status
	 *            - Status(pending/published/approved/decline) of a blog.
	 * @param userId
	 *            - userId of particular User
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 */
	public List<Blog> findBlogByStatusAndUserId(String status, int userId, int min, int max);

	/**
	 * @param blogStatus
	 *            - to set Status(decline) of a blog.
	 * @param reason
	 *            - reason to declined a blog by admin
	 * @param blogId
	 *            - blog Id of particular blog to be declined
	 */
	public int updateStatusWithReasonByBlogId(String blogStatus, String reason, int blogId) throws FixitException;

	/**
	 * this method used to delete a blog but should be call after deleting its
	 * child(blogCategory)
	 * 
	 * @param blogId
	 *            - blogId of a particular blog
	 * 
	 */
	public int deleteBlogByBlogId(int blogId) throws FixitException;

}
