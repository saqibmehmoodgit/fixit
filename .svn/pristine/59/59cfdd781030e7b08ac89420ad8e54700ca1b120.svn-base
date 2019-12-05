package com.fixit.dao;

import java.util.List;

import org.skyway.spring.util.dao.JpaDao;

import com.fixit.domain.vo.BlogCategory;
import com.fixit.utility.FixitException;

public interface BlogCategoryDao extends JpaDao<BlogCategory> {
	/**
	 * this method delete child of Blog i.e. All categories associated with Blog
	 * 
	 * @param blogId
	 *            - blogId of a particular blog
	 * @return 1 if success
	 */
	public int deleteBlogCategoryByBlogId(int blogId) throws FixitException;

	/**
	 * this method used to fetch all categories that are associated with a blog
	 * 
	 * @param blogId
	 *            - blogId of a particular blog
	 * @return List of object of type BlogCategories of a particular blog
	 */
	public List<BlogCategory> categoriesByBlogId(int blogId) throws FixitException;

	/**
	 * This method used to filter record with a categories.
	 * 
	 * @param userId
	 *            - userId of a particular user from session.
	 * @param catId
	 *            - catId Of particular categories of a blog
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 * @return List of object of blogCategories type filtered by categories.
	 */
	public List<BlogCategory> blogByCategories(int userId, int catId, int min, int max) throws FixitException;

	/**
	 * @param catId
	 *            - catId Of particular categories of a blog.
	 * @param blogStatus
	 *            - Status(pending/published/approved/decline) of a blog.
	 * 
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned.
	 */
	public List<BlogCategory> findBlogByStatusAndCategories(int catId, String blogStatus, int min, int max)
			throws FixitException;

	/**
	 * @param catId
	 *            - catId Of particular categories of a blog.
	 * @param userId
	 *            - userId of a particular user.
	 * 
	 * @param blogStatus
	 *            - Status(pending/published/approved/decline) of a blog.
	 * @param min
	 *            - the first row to be returned.
	 * @param max
	 *            - maxResults the maximum number of rows to be returned. 
	 * @return List of object of BlogCategories filtered with catId And userId
	 */
	public List<BlogCategory> findBlogByUserIdStatusAndCategories(int catId, String blogStatus, int userId, int min,
			int max) throws FixitException;

}
