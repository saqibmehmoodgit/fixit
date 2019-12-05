/*package com.fixit.utility;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class HighLevelApiCode {

    public static void main(String[] args) throws Exception {
        String existingBucketName = "*** Provide existing bucket name ***";
        String keyName            = "*** Provide object key ***";
        String filePath           = "*** Path to and name of the file to upload ***";  
        
        TransferManager tm = new TransferManager(new ProfileCredentialsProvider());        
       
        // TransferManager processes all transfers asynchronously, 
        // so this call will return immediately.
        Upload upload = tm.upload(existingBucketName, keyName, new File(filePath));
       

        try {
        	// Or you can block and wait for the upload to finish
        	upload.waitForCompletion();
        	
        } catch (AmazonClientException amazonClientException) {
        	
        	amazonClientException.printStackTrace();
        }
    }
}*/