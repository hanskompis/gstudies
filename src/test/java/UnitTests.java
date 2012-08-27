/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.*;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import tktl.gstudies.services.GraphicsService;

/**
 *
 * @author hkeijone
 */
public class UnitTests {
    
    @Autowired
     private GraphicsService graphicsService;
    
    public UnitTests() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void maxAmountCoursesWorks() {
        
    }
}
