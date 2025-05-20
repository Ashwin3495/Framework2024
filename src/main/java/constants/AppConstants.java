package constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	public static final String DEFAULT_PAGE_TITLE="Account Login";
	public static final String HOME_PAGE_TITLE="My Account";
	
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String HOME_PAGE_FRACTION_URL = "route=account/account";
	
	public static final int DEFAULT_TIMEOUT=5;
	public static final int MEDIUM_TIMEOUT=10;
	public static final int LONG_TIMEOUT=15;
	
//	When storing in a list it must look like this
	public static final List<String> expectedheaders = Arrays.asList(
		    "My Account",
		    "My Orders",
		    "My Affiliate Account",
		    "Newsletter"
		);
	
	public static final String SUCCESS_MESSAGE="Your Account Has Been Created!";
	
	
//**************** Sheet Util ********************
	
	public static final String REGISTER_SHEET_NAME="register";
	public static final String PRODUCT_SHEET_NAME="product";
	public static final String PRODUCTINFO_SHEET_NAME="productinfo";
	
}
