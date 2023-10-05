import java.util.ArrayList;
import java.util.List;

import model.orders.Order;

public class orderTest {

    private List<Order> d_OrderArray = new ArrayList<Order>();
    private String d_OrderType;

    Order order =new Order();
    OrderDetails orderInfo = new OrderDetails();
    List<Order> orderList = new ArrayList<Order>();
    String type;
    
    @Before
    public void setUp() throws Exception {
        c1.setCountryName("India");
        c2.setCountryName("China");
        c3.setCountryName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(c1);           
        d_CapturedCountries.add(c2);
        d_CapturedCountries.add(c3);
        p.setOccupiedCountries(d_CapturedCountries);
    }
      @After
    public void tearDown() throws Exception {
        d_GameMap.getContinents().clear();
        d_GameMap.getCountries().clear();
        d_GameMap.getGamePlayers().clear();
    }
      @Test
    public void testValidCheckIfCountryExists() {
        assertTrue(p.confirmIfCountryisOccupied(d_CountryValid,p));
    }
    /**
     * This is the test method to check if Country does not exist
     *
     */
    @Test
    public void testInvalidCheckIfCountryExists() {
        assertFalse(p.confirmIfCountryisOccupied(d_CountryInvalid,p));
    }
}
