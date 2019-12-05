package com.fixit.utility;

import org.springframework.web.servlet.ModelAndView;

public class Pagination {
    
	 public   static  ModelAndView  paginate(ModelAndView mav , Integer pageNo , String flag , int totalPage){
		switch (flag) {
			case "left":
				if(pageNo%DbConstants.PAGE_NO_DISPLAY==1){
					mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*((pageNo/DbConstants.PAGE_NO_DISPLAY)-1))+1);
					if(((((pageNo/DbConstants.PAGE_NO_DISPLAY)-1)+1)*DbConstants.PAGE_NO_DISPLAY)>=totalPage){
						mav.addObject("endPage",totalPage);
					}else{
						mav.addObject("endPage",(((pageNo/DbConstants.PAGE_NO_DISPLAY)-1)+1)*DbConstants.PAGE_NO_DISPLAY);
					}
						
				}else{
				    if(pageNo%DbConstants.PAGE_NO_DISPLAY==0){
				    	mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*((pageNo/DbConstants.PAGE_NO_DISPLAY)-1))+1);
				    	if(((((pageNo/DbConstants.PAGE_NO_DISPLAY)))*DbConstants.PAGE_NO_DISPLAY)>=totalPage){
				    		mav.addObject("endPage",totalPage);
				    	}else{
				    		mav.addObject("endPage",(((pageNo/DbConstants.PAGE_NO_DISPLAY)))*DbConstants.PAGE_NO_DISPLAY);
				    	}
							
				    }else{
				    	mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*((pageNo/DbConstants.PAGE_NO_DISPLAY)))+1);
				    	if(((((pageNo/DbConstants.PAGE_NO_DISPLAY)+1))*DbConstants.PAGE_NO_DISPLAY)>=totalPage){
				    		mav.addObject("endPage",totalPage);	
				    	}else{
				    		mav.addObject("endPage",(((pageNo/DbConstants.PAGE_NO_DISPLAY)+1))*DbConstants.PAGE_NO_DISPLAY);
				    	}
							
				    }
	         }	
				mav.addObject("currentPageNo", pageNo-1);
				
				break;
				
	       case "right":
	    	   if(pageNo>0){

					if( pageNo+DbConstants.PAGE_NO_DISPLAY>totalPage){
						if(pageNo%DbConstants.PAGE_NO_DISPLAY==0){
						    	mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*((pageNo/DbConstants.PAGE_NO_DISPLAY)))+1);
								mav.addObject("endPage",totalPage);
						}else{
							mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*(pageNo/DbConstants.PAGE_NO_DISPLAY))+1);
							if((((pageNo/DbConstants.PAGE_NO_DISPLAY)+1)*DbConstants.PAGE_NO_DISPLAY)>=totalPage){
								mav.addObject("endPage",totalPage);
							}else{
								mav.addObject("endPage",((pageNo/DbConstants.PAGE_NO_DISPLAY)+1)*DbConstants.PAGE_NO_DISPLAY);	
							}
							
						}
					}else{
						if(pageNo%DbConstants.PAGE_NO_DISPLAY==0){
							mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*(pageNo/DbConstants.PAGE_NO_DISPLAY))+1);
							mav.addObject("endPage",((pageNo/DbConstants.PAGE_NO_DISPLAY)+1)*DbConstants.PAGE_NO_DISPLAY);	
						}else{
							mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*(pageNo/DbConstants.PAGE_NO_DISPLAY))+1);
							mav.addObject("endPage",((pageNo/DbConstants.PAGE_NO_DISPLAY)+1)*DbConstants.PAGE_NO_DISPLAY);	
						}
				    }
					mav.addObject("currentPageNo", pageNo+1);
					
				}else{
					 mav.addObject("startPage",1);
					if(DbConstants.PAGE_NO_DISPLAY<=totalPage){
						mav.addObject("endPage",DbConstants.PAGE_NO_DISPLAY);
					}else{
						mav.addObject("endPage",totalPage);
					}
					mav.addObject("currentPageNo", 1);
					
				}


				break;	
				
	       case "current":
	    	   if(pageNo%DbConstants.PAGE_NO_DISPLAY==0){
					mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*((pageNo/DbConstants.PAGE_NO_DISPLAY)-1))+1);
					if((((pageNo/DbConstants.PAGE_NO_DISPLAY))*DbConstants.PAGE_NO_DISPLAY)>=totalPage){
						mav.addObject("endPage",totalPage);
					}else{
						mav.addObject("endPage",((pageNo/DbConstants.PAGE_NO_DISPLAY))*DbConstants.PAGE_NO_DISPLAY);	
					}
						
				}else{
					mav.addObject("startPage",(DbConstants.PAGE_NO_DISPLAY*(pageNo/DbConstants.PAGE_NO_DISPLAY))+1);
					if((((pageNo/DbConstants.PAGE_NO_DISPLAY)+1)*DbConstants.PAGE_NO_DISPLAY)>=totalPage){
						  mav.addObject("endPage",totalPage);
					}else{
						mav.addObject("endPage",((pageNo/DbConstants.PAGE_NO_DISPLAY)+1)*DbConstants.PAGE_NO_DISPLAY);	
					}
						
				}
	    	   mav.addObject("currentPageNo", pageNo);
				break;	

			default:
				break;
			}
			
			mav.addObject("totalPage", totalPage);
		   return mav;
	 }
	 
	 public static Integer findStartIndex(Integer pageNo , String flag){
			int startIndex;
			if(pageNo==0){
				startIndex = 0;
			}else{
				if(flag.equals("left")){
					 startIndex = (pageNo-2)*DbConstants.PAGE_SIZE;	
				}else{
					 if(flag.equals("right")){
					 startIndex = (pageNo)*DbConstants.PAGE_SIZE;
					 }else{
						 startIndex = (pageNo-1)*DbConstants.PAGE_SIZE; 
					 }
				}	
			}
			return startIndex;
	 }
}
