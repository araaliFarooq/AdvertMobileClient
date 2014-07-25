package com.menu.android;

public class NewUser {
		    
		private String userName;
	    private String password;
	    private String emailAddress;
	    private String phoneNumber;

	    public void setPassword(String password) {
		    this.password = password;
	    }
	    public void setUserName(String userName) {
		    this.userName = userName;
	    }
	    public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
	    public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
	     public String getPassword() {
		    return password;
	    }
	    public String getUserName() {
		    return userName;
	    }
	    public String getEmailAddress() {
			return emailAddress;
		}
	    public String getPhoneNumber() {
			return phoneNumber;
		}
			
		@Override
		public String toString() {
			return "NewUser [emailAddress="+emailAddress+"phoneNumber="+phoneNumber+"userName="+userName+"password="+password+"]";
		}

}
