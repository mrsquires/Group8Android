package library.group8.constant;
  public abstract class SQLCommand  
 {
	//list all data in books table
	public static String QUERY_1 = "select lbcallnum, lbtitle from libbook";
	//List the call numbers of books with the title ‘Database Management’
	public static String QUERY_2 = "select lbcallnum from libbook where lbtitle like '%Database Management%'";
	//Select all data in the libbook table
	public static String QUERY_3 = "select lbcallnum from libbook";
	//Select all data in the libbook table
	public static String QUERY_4 = " select stid from Student ";
	//Select all data in the libbook table
	public static String QUERY_5 = " select lbtitle from libbook ";
	//Select all data in the libbook table
	public static String QUERY_6 = " select stname from Student ";
	//Select all data in the libbook table
	public static String QUERY_7 = " select * from Student ";
	//Return a Book
	public static String RETURN_BOOK = "update checkout set coreturned=? where stid=? and lbcallnum=?";
	//Check Out a Book
	public static String CHECK_BOOK = "insert into checkout(stid,lbcallnum,coduedate,coreturned) values(?,?,?,?)";
	//checkout summary
	public static String CHECKOUT_SUMMARY = "select strftime('%m',coduedate) as month,count(*) as total from checkout where strftime('%Y',coduedate)='2011' group by month order by total desc";

}
