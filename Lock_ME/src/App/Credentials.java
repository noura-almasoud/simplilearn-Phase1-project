package App;

public class Credentials {

	private String SiteName;
	private String SiteUserName;
	private String SitePassword;
	
	
	public Credentials() {
	}
	
	public Credentials(String SiteName, String SiteUserName, String SitePassword) {
		this.SiteName = SiteName;
		this.SiteUserName = SiteUserName;
		this.SitePassword = SitePassword;
	}
	

	public String getSiteName() {
		return SiteName;
	}

	public void setSiteName(String siteName) {
		SiteName = siteName;
	}
	

	public String getSiteUserName() {
		return SiteUserName;
	}

	public void setSiteUserName(String siteUserName) {
		SiteUserName = siteUserName;
	}
	

	public String getSitePassword() {
		return SitePassword;
	}

	public void setSitePassword(String sitePassword) {
		SitePassword = sitePassword;
	}
	
}
