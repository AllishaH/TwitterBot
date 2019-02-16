import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.*;
import java.io.*;

public class TwitchTwitterBot {
	
    public static void main(String[] args) {
   		
	    Timer time = new Timer(); //Timer Object
		ScheduledTask st = new ScheduledTask(); //Instantiate SheduledTask class
		time.schedule(st, 0, 15000); //Create Scheduledtask for every 15 seconds			
	}

    static class ScheduledTask extends TimerTask  {

    	Date now; //to display current time

    	public void run()  {
    		
    		now = new Date(); //initialize date
    		System.out.println("Time is :" + now);

    		try{
    	    TwitterSR(); 
    		}
    	    	catch (InterruptedException e){
    	    		System.out.println(e);
    	    	}	 
    			catch (FileNotFoundException e){
    				System.out.println(e);
    			}	 
    			catch (TwitterException e){
    				//code 139 means a tweet found is already liked
    				if(e.getMessage().contains("code - 139"))  {
    				}
    				//code 88 means twitter4j rate exceeded
    				if(e.getMessage().contains("code - 88"))  {
    					System.out.println("YOUR CODE IS IN TIME OUT >:|");
    					//Pause for 5 minutes
    					try {
    						Thread.sleep(300000);
    						//System.exit(1);
    					}
    					catch (InterruptedException e1){
    					}
    				}
    			}
    	}
    }
  	  
//T w i t t e r S R
 	
    private static String TwitterSR() throws TwitterException, FileNotFoundException, InterruptedException {    		
    	
    	TwitterFactory tf = new TwitterFactory();
   		Twitter twitter = tf.getInstance();
  		
   		//Credentials
	   	Scanner cred = new Scanner(new File ("cred.txt"));
	   	String consumerKey = cred.nextLine();
	   	String consumerSecret = cred.nextLine();
	   	String accessToken = cred.nextLine();
	   	String accessTokenSecret = cred.nextLine();
	   	cred.close();
	    //setup OAuth Consumer Credentials
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);
	    //setup OAuth Access Token
	    twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	   	
	    //Search for related tweet
	    String relatedTweet = "";
	    String User = "";
	    while ((relatedTweet == "") || (relatedTweet.contains("..."))) {
//Change #twitch to whatever hashtag you'd like to target
	    	Query queryTweet = new Query("#twitch");
	    	queryTweet.count(1);
	    	QueryResult resultTweet = twitter.search(queryTweet);
	    	for (Status status : resultTweet.getTweets()) {
	    		relatedTweet = status.getText();
	    		User = "@" + status.getUser().getScreenName();
		
	    		//Favorite the tweet
	    		twitter.createFavorite(status.getId());
	        	System.out.println("");
	        	System.out.println("");
	    		System.out.println("You liked this tweet! <3");
	    		System.out.println(User + ":" + relatedTweet);
	    		System.out.println("");
	    		System.out.println("");
	    		System.out.println("");
	    	}
	    }
	    
	   return User; //exit
    } 
} 			

