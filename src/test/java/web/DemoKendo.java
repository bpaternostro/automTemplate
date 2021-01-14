package web;

import com.everis.data.ExcelUtils;
import com.everis.powebtenaris.TenarisKendoIndex;
import com.everis.powebtenaris.TenarisKendoMain;
import com.everis.powebtenaris.TenarisKendoOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;


public class DemoKendo extends BaseTest{

    final static Logger log = LogManager.getLogger(DemoKendo.class);

    @DataProvider
    public Object[][] KendoCases(ITestContext context) throws Exception{
        Object[][] testObjArray = ExcelUtils.getTableArray(super.getDataProviderDir()+context.getCurrentXmlTest().getParameter("dataProvider"),context.getCurrentXmlTest().getParameter("sheet"));
        return (testObjArray);
    }

    @Test(description="Ejecuto kendo test",dataProvider="KendoCases")
    public void kendoTest(String sUsername,String sPassword,String nroOrden, String family, String int_norm, String steel_grade,
                            String customer_spec, String heat_treatment, String process, String forming,
                            String od_mm, String od_Inch, String internal_diameter_mm, String internal_diameter_inches,
                            String wT_mm, String wall_thickness_in_inches, String ends, String length, String length_from_in_mm,
                            String length_from_in_ft, String length_to_in_mm, String length_to_in_ft, String estimated_length_piece_mm,
                            String estimated_length_piece_ft, String inside_surface_protection, String outside_surface_protection,
                            String finishing_quantity, String finish, String steel_class, String end_protector, String packing, String ppf,
                            String delivery_conditions, String delivery_date, String operation_type, String additional_address, String qty,
                            String qty_unit, String qty_in_tons, String tolerance_neg, String tolerance_pos, String tol_unit, String customer_item_number,
                            String awarding_probability, String target, String status, String comment, String incoterm_freight_condition, String price_segment,
                            String price_List,String price_Date) throws InterruptedException {

        //String nroOrden="0080055935";
        int copiasOrdenes = 2;

        log.info("Inicia el caso de prueba");
        log.info("Ejecutando " + sUsername);

        log.info("Ejecuto Click on salesLink");
        TenarisKendoMain main = new TenarisKendoMain(driver);
        main.clickSalesLink();

        log.info("Obtengo la orden de la lista de resultados");
        TenarisKendoIndex index = new TenarisKendoIndex(driver);
        Assert.assertTrue(index.searchOrder(nroOrden),"Valido si fue posible seleccionar la orden.");

        log.info("Trabajo sobre la orden");
        TenarisKendoOrder order = new TenarisKendoOrder(driver);
        order.goToProducts();

        String[] valores= new String[] {family, int_norm, steel_grade,
                customer_spec, heat_treatment, process, forming,
                od_mm, od_Inch, internal_diameter_mm, internal_diameter_inches,
                wT_mm, wall_thickness_in_inches, ends, length, length_from_in_mm,
                length_from_in_ft, length_to_in_mm, length_to_in_ft, estimated_length_piece_mm,
                estimated_length_piece_ft, inside_surface_protection, outside_surface_protection,
                finishing_quantity, finish, steel_class, end_protector, packing, ppf,
                delivery_conditions, delivery_date, operation_type, additional_address, qty,
                qty_unit, qty_in_tons, tolerance_neg, tolerance_pos, tol_unit, customer_item_number,
                awarding_probability, target, status, comment, incoterm_freight_condition, price_segment, price_List, price_Date,""};

        Assert.assertTrue(order.addProduct(valores), "Valido si se agrego correctamente el producto.");
        Assert.assertTrue(order.validarProducto(), "Valido si se chequeo correctamente el producto.");
        Assert.assertTrue(order.seleccionarUltimoProducto(), "Valido si se seleccionó correctamente el producto.");
        Assert.assertTrue(order.copiarProducto(copiasOrdenes), "Valido si se copió correctamente el producto.");
        Assert.assertTrue(order.validarSpansoInRows(copiasOrdenes + 1), "Valido si se validaron todos los registros.");
        Assert.assertTrue(order.borrarRegistro(), "Valido si se borró correctamente el ultimo registro.");

        System.out.println("El proceso finalizó correctamente.");

    }

}

//spanso = (String) jsExecutor.executeScript("return arguments[0].value;", tds.get(tds.size()-1));