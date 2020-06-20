package srp;

import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;

import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import srp.config.DBConnectionManager;
import srp.controllers.CategoryController;
import srp.controllers.CustomerController;
import srp.controllers.OrderController;
import srp.controllers.ProductController;
import srp.controllers.FamilyController;

import srp.repositories.impl.*;

public class App {
    private final DBConnectionManager manager;
    private final CustomerController customerController;
    private final OrderController orderController;
    private final ProductController productController;
    private final CategoryController categoryController;
    private final FamilyController familyController;

    public App() {
        this.manager = new DBConnectionManager();

        CustomerRepositoryImpl customerRepositoryImpl = new CustomerRepositoryImpl(this.manager.getDatabase());
        this.customerController = new CustomerController(customerRepositoryImpl);
        
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl(this.manager.getDatabase());
        this.orderController = new OrderController((orderRepositoryImpl));

        ProductRepositoryImpl productRepositoryImpl = new ProductRepositoryImpl(this.manager.getDatabase());
        this.productController = new ProductController((productRepositoryImpl));

        CategoryRepositoryImpl categoryRepositoryImpl = new CategoryRepositoryImpl(this.manager.getDatabase());
        this.categoryController = new CategoryController((categoryRepositoryImpl));
        
        FamilyRepositoryImpl familyRepositoryImpl = new FamilyRepositoryImpl(this.manager.getDatabase());
        this.familyController = new FamilyController((familyRepositoryImpl));

    }

    public static void main(String[] args) {
        new App().startup();
    }

    public void startup() {
        
        Info applicationInfo = new Info().version("1.0").description("Demo API");
        
        OpenApiOptions openApi = new OpenApiOptions(applicationInfo)
                .path("/api")
                .swagger(new SwaggerOptions("/api-ui"));
        
        Javalin server = Javalin.create(config -> {
            config.registerPlugin(new OpenApiPlugin(openApi));
            config.defaultContentType = "application/json";
        }).start(7000);
        
        // METHODS CUSTOMER
        server.get("api/customer/:id", this.customerController::find);
        server.delete("api/customer/:id", this.customerController::delete);
        server.get("api/customers", this.customerController::findAll);
        server.post("api/customer", this.customerController::create);
        
        // METHODS ORDER
        server.get("api/order/:id", this.orderController::find);
        server.delete("api/order/:id", this.orderController::delete);
        server.get("api/orders", this.orderController::findAll);
        server.post("api/order", this.orderController::create);

        // METHODS PRODUCTS
        server.get("api/product/:id", this.productController::find);
        server.delete("api/product/:id", this.productController::delete);
        server.get("api/products", this.productController::findAll);
        server.post("api/product", this.productController::create);

         // METHODS CATEGORYS
         server.get("api/category/:id", this.categoryController::find);
         server.delete("api/category/:id", this.categoryController::delete);
         server.get("api/categorys", this.categoryController::findAll);
         server.post("api/category", this.categoryController::create);

         // METHODS CATEGORYS
         server.get("api/family/:id", this.familyController::find);
         server.delete("api/family/:id", this.familyController::delete);
         server.get("api/familys", this.familyController::findAll);
         server.post("api/family", this.familyController::create);

        server.get("/hello", ctx -> ctx.html("Hello, Javalin!"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.manager.closeDatabase();
            server.stop();
        }));

    }

}