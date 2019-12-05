package com.fixit.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fixit.domain.bo.BlogBo;
import com.fixit.domain.vo.Blog;
import com.fixit.domain.vo.BlogCategory;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface BlogService {
	/**
	 * return an blog object that is to be save by user
	 * <p>
	 * 
	 * @param user
	 *            -to get userId from session
	 * @param blogBo
	 *            -set to Model POJO class
	 * @return Blog -return blog object
	 * 
	 */
	public Blog loadBlog(BlogBo blogBo, User user) throws FixitException;

	/**
	 * return the list of blog object by status
	 * 
	 * @param Status
	 *            - fetch blog by its status
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * @return list of blog object by its status
	 */
	public java.util.List<Blog> findBlogDataByStatus(String Status, int min, int max);

	/**
	 * 
	 * used to update blog status by admin
	 * 
	 * @param blogStatus
	 *            - status used to update the status of blog.
	 * @param blogId
	 *            - blogId of a particular blog to updated by admin.
	 * @return return 1 if success.
	 */
	public int updateStatusByBlogId(String blogStatus, int blogId) throws FixitException;

	/**
	 * used to update the status of blog with reason when declined by admin.
	 * 
	 * @param blogStatus
	 *            - used to set status of blog.
	 * @param reason
	 *            - reason of a blog, declined by admin.
	 * @param blogId
	 *            - blogId of blog to be declined by admin
	 * @return 1 if success
	 **/
	public int updateStatusWithReasonByBlogId(String blogStatus, String reason, int blogId) throws FixitException;

	/**
	 * return list of object of all blog.
	 * 
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 */
	public List<Blog> findAllBlog(int min, int max) throws FixitException;

	/**
	 * return an list of blog object by userId of a particular user
	 * <p>
	 * 
	 * @param userId
	 *            - user from session
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * @return List<Blog> -return list of blog object by userId
	 * 
	 */
	public List<Blog> findAllBlogByUserId(int userId, int min, int max) throws FixitException;

	/**
	 * return blog object by blogId
	 * <p>
	 * 
	 * @param blogId
	 *            - blog id of a particular blog
	 * @return blog - return blog object
	 * 
	 */
	public Blog findAllBlogByBlogId(int blogId) throws FixitException;

	/**
	 * Used for pagination to display limited number of record in Views.
	 * 
	 * @param pageNo
	 *            - page number From Views.
	 * @param flag
	 *            - left/right/current pagination button.
	 * @param totalPage
	 *            -set maximum result .
	 * @param mav
	 *            - to set model attribute for pagination. Holder for both Model
	 *            and View in the web MVC framework.
	 *            <p>
	 *            Represents a model and view returned by a handler, to be
	 *            resolved by a DispatcherServlet. The view can take the form of
	 *            a String view name which will need to be resolved by a
	 *            ViewResolver object; alternatively a View object can be
	 *            specified directly. The model is a Map, allowing the use of
	 *            multiple objects keyed by name.
	 * 
	 */
	public void blogPagination(int pageNo, String flag, ModelAndView mav, int totalPage) throws FixitException;

	/**
	 * This method used to edit the blog after being save by user return an blog
	 * object that is to be save by user
	 * <p>
	 * 
	 * @param blogId
	 *            - to get the blog that is to be edited by user
	 * @param blogBo
	 *            - used to set response String to Model POJO class
	 * @return Blog -return blog object
	 * 
	 */
	public Blog updateBlog(BlogBo blogBo, int blogId) throws FixitException;

	/**
	 * 
	 * return list of blog object of a particular user with a particular status
	 * of blog
	 * 
	 * @param userId
	 *            - userId of user from session
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * @param status
	 *            - used to fetch blog by its status
	 * @return List of blog object of a particular user with a particular status
	 *         of blog
	 */

	public List<Blog> findBlogByStatusAndUserId(String status, int userId, int min, int max) throws FixitException;

	/**
	 * Delete the child(sap categories of blog) first then parent(Blog) node
	 * from database.
	 * 
	 * <p>
	 * 
	 * @param blogId
	 *            - to get the blog that is to be edited by user
	 * @return Integer - return 1 if success
	 * 
	 */

	public int deleteBlogAndBlogCategoryByBlogId(int blogId);

	/**
	 * delete all blog by userId Of a particular user.
	 * 
	 * @param userId
	 *            - userId of a user
	 * 
	 */
	public int deleteBlogByUserId(int userId);

	/**
	 * return SAP categories of a particular blog.
	 * 
	 * @param blogId
	 *            - blogId of a particular blog.
	 * @return categoried by blogId of a particular blog.
	 */

	public List<BlogCategory> categoriesByBlogId(int blogId) throws FixitException;

	/**
	 * return location of image to be uploaded on aws s3 client. throws
	 * IOException file is not of multipart type. throws InterruptedException if
	 * aws authentication/uploading failed.
	 * 
	 * @param userName
	 *            - userName of a user from session.
	 * @param file
	 *            - image file to be uploaded from blog .
	 * @return location of image to be uploaded on aws s3 client.
	 */
	public String uloadBlogFile(String userName, MultipartFile file)
			throws IOException, FixitException, InterruptedException;

	/**
	 * return filter List of blog by sap categories of particular blog.
	 * 
	 * @param userId
	 *            - userId of a particular user from session.
	 * @param catId
	 *            - catId of particular categories used to filter the blog by
	 *            categories.
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * @return filter List of blog by sap categories.
	 */
	public List<Blog> blogsByUserIdCatId(int userId, int catId, int min, int max) throws FixitException;

	/**
	 * @param userId
	 *            - userId of a particular user from session.
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * 
	 */
	public List<Blog> findBlogByStatusUserIdCatId(String status, int userId, int catId, int min, int max)
			throws FixitException;

	/**
	 * return list of blog object filter by its categories and its status
	 * 
	 * @param catId
	 *            - catId of particular categories used to filter the blog by
	 *            categories.
	 * @param Status
	 *            - used to fetch blog by its status
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * @return list of blog object filter by its categories and its status
	 */
	public List<Blog> findBlogByStatusAndCategories(int catId, String Status, int min, int max) throws FixitException;

	/**
	 * return list of blog object filtered by a particular user.
	 * 
	 * @param userId
	 *            - userId of user from session.
	 * @param catId
	 *            - catId of particular categories used to filter the blog by
	 *            categories.
	 * @param blogStatus
	 *            - used to fetch blog by its status
	 * @param min
	 *            -set minimum rows
	 * @param max
	 *            -set maximum result
	 * @return list of blog object filtered by a particular user.
	 */
	public List<Blog> findBlogByStatusUserIdAndCategories(int catId, String blogStatus, int userId, int min, int max)
			throws FixitException;


}
