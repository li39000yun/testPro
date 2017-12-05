package com.lyq.sellercenter;

import com.alibaba.normandie.api.core.LazadaClient;
import com.alibaba.normandie.api.core.exception.LazadaException;
import com.alibaba.normandie.api.endpoint.product.model.Attribute;
import com.alibaba.normandie.api.endpoint.product.model.AttributeOption;
import com.alibaba.normandie.api.endpoint.product.model.Category;
import com.alibaba.normandie.api.endpoint.product.model.product.Product;
import com.alibaba.normandie.api.endpoint.product.model.product.ProductsFilter;
import com.alibaba.normandie.api.endpoint.product.model.product.Sku;
import com.alibaba.normandie.api.endpoint.product.request.CreateProduct;
import com.alibaba.normandie.api.endpoint.product.request.GetCategoryAttributes;
import com.alibaba.normandie.api.endpoint.product.request.GetCategoryTree;
import com.alibaba.normandie.api.endpoint.product.request.GetProducts;
import com.alibaba.normandie.api.endpoint.product.request.body.ProductBody;
import com.alibaba.normandie.api.endpoint.product.response.GetCategoryAttributesResponse;
import com.alibaba.normandie.api.endpoint.product.response.GetCategoryTreeResponse;
import com.alibaba.normandie.api.endpoint.product.response.GetProductsResponse;
import com.alibaba.normandie.api.endpoint.product.response.ModifyProductResponse;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API测试
 * Lazada（来赞达），东南亚地区最大的在线购物网站之一
 * Created by 云强 on 2017/12/4.
 */
public class ApiTest {


    // 马来西亚临时环境
    static String test_url = "https://api-staging.sellercenter.lazada.com.my/";

    // 个人密钥
    static String test_apiKey = "t_WIw5u7XsaWfuByL0H0P8w4QK5xNiMMzc6SB_75xFYxENls7aI4ZAEH";

    // 邮箱
    static String test_email = "li39000yun@qq.com";

    // 账户
    static String accound = "lyq";


    // 马来西亚正式环境
    static String url = "https://api.sellercenter.lazada.com.my/";
    // 个人密钥
    static String apiKey = "POnJqnoxCNQ5731evV9GQgosgP4CBuWvtlAtUYzlrOHdlsH0U3BMR0R6";
    // 邮箱
    static String email = "1376187082@qq.com";

    public static void main(String[] args) {

        //init global default client with venture URL, your email and API key
//        LazadaClient.init("https://api.sellercenter.lazada.com.my/","test@126.com", "3aac65392ce5ebdfa7eadf4933c9316a4810581e");
//        LazadaClient.init(url, email, apiKey);
        LazadaClient.init(test_url, test_email, test_apiKey);

        // 获取产品
//        getProducts();
        // 创建产品
        createProduct();
        // 获取类型
//        getCategoryTree();
        // 获取类型属性
//        getCategoryAttributes();

//then forget all about signing, just fire your request

        //Example
        //1.query order by ID
        //init request with orderId
//        GetProducts reque = new GetProducts();
//        reque.setAction();
//        GetOrder request = new GetOrder(204590833L);
//        //fire request and handle result Order
//        try {
//            GetOrderResponse response = request.execute();
//            System.out.println(response.getBody());
//        } catch (LazadaException e) {
//            System.out.println(e.getResponseStr());
//        }

//        SearchSPUs searchSPUs = new SearchSPUs("s");
//        try {
//            SearchSPUsResponse response = searchSPUs.execute();
//            System.out.println(response.getBody());
//        } catch (LazadaException e) {
//            System.out.println(e.getResponseStr());
//        }

        //2.upload image
        //construct request with local file
//        File image = new File("D:\\work\\learning\\xb.jsp");
//        UploadImage uploadImage = new UploadImage(image);
//        try {
//            ModifyImageResponse response = uploadImage.execute();
//            System.out.println("New Url: " + response.getBody().getImage().getUrl());
//            System.out.println("HashCode: " + response.getBody().getImage().getCode());
//        } catch (LazadaException e) {
//            System.out.println(e.getResponseStr());
//        }

    }

    /**
     * 获取产品
     */
    private static void getProducts() {
        ProductsFilter filter = new ProductsFilter();

        GetProducts getProducts = new GetProducts(filter);
        try {
            GetProductsResponse response = getProducts.execute();
//            List<Product> products = response.getBody();
//            for (int i = 0; i < products.size(); i++) {
//                System.out.println(products.get(i).getAttributes().getName());
//            }
            System.out.println(response.getBody());
            System.out.println( JSONArray.fromObject(response.getBody()).toString());
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }

    /**
     * 获取类别属性
     */
    private static void getCategoryAttributes() {
        GetCategoryAttributes getCategoryAttributes = new GetCategoryAttributes(3L);
        try {
            GetCategoryAttributesResponse response = getCategoryAttributes.execute();
            List<Attribute> attributes = response.getBody();
            for (Attribute attribute : attributes) {
                if (attribute.isMandatory()) {
                    System.out.println(attribute.getName() + ";" + attribute.getInputType() + ";" + attribute.getAttributeType() + ";" + attribute.getOptions());
                }
            }
            System.out.println(response.getBody());
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }

    /**
     * 获取产品类别
     */
    private static void getCategoryTree() {
        GetCategoryTree get = new GetCategoryTree();
        try {
            GetCategoryTreeResponse response = get.execute();
            List<Category> categorys = response.getBody();
            for (Category category : categorys) {
                System.out.println(category.getName() + ":" + category.getCategoryId()+";");
                if (category.getCategoryId() ==2) {
                    System.out.println(category.getChildren().size());
                    for(Category c : category.getChildren()) {
                        System.out.println(c.getName() + ":" + c.getCategoryId()+";"+c.getChildren());
                    }
                }
            }
            System.out.println(response.getBody());
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }


    /**
     * 新增产品
     */
    private static void createProduct() {


        //CreateProduct requires three piece of information
//1.Category Id --- which can be queried by GetCategoryTree
//2.Product attributes --- whose key set and value set can be queried by GetCategoryAttributes
//3.one or more Sku(s) attributes --- the set and value set can be queried by GetCategoryAttributes

//construct attributes of product
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("warranty_type", "No Warranty");
        attributes.put("package_height", 11.1);
        attributes.put("short_description", "yucheng CreateProduct test");
        attributes.put("name", "yucheng's first product");
        attributes.put("name_ms", "yucheng's first product");
        attributes.put("model", "test model");
        attributes.put("brand", "Huawei");
        attributes.put("display_size_mobile", "4.3");
        attributes.put("operating_system_version", "Android 5.1 Lollipop");
        attributes.put("operating_system","Android");

//construct SKUs
        List<Map<String, Object>> skusList = new ArrayList<Map<String, Object>>();
//        Map<String, Object> sku1 = new HashMap<String, Object>();
//        sku1.put("SellerSku", "blue");
//        sku1.put("package_height", 11.1);
//        sku1.put("short_description", "yucheng CreateProduct test");
//        sku1.put("package_width", 5.9);
//        sku1.put("tax_class", "default");
//        sku1.put("package_content", "empty");
//        sku1.put("package_weight", 11.9);
//        sku1.put("package_length", 19.3);
//        sku1.put("color_family", "Blue");
//        sku1.put("storage_capacity_new", "128G");
//        skusList.add(sku1);

        Map<String, Object> sku2 = new HashMap<String, Object>();
        sku2.put("SellerSku", "black");
        sku2.put("package_height", 11.1);
        sku2.put("short_description", "yucheng CreateProduct test");
        sku2.put("package_width", 5.9);
        sku2.put("tax_class", "default");
        sku2.put("package_content", "empty");
        sku2.put("package_weight", 11.9);
        sku2.put("package_length", 19.3);
        sku2.put("color_family", "Blue");
        sku2.put("storage_capacity_new", "64GB");
        skusList.add(sku2);

//construct request by categoryId, attributes of product, attributes of sku(s)
        CreateProduct createProduct = new CreateProduct(3L, attributes, skusList);
        try {
            ModifyProductResponse response = createProduct.execute();
            System.out.println(String.format("CreateProduct succeeded?%b",response.getBody()));
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }


//        Long categoryId = 3L;// 产品id
//        Map<String, Object> productAttributes = new HashMap<String, Object>();// 产品属性
//        List<Map<String, Object>> skusAttributes = new ArrayList<Map<String, Object>>();// sku信息
//
//
//        productAttributes.put("name", "apple");
//        productAttributes.put("short_description","简单易用");
//        productAttributes.put("SellerSku","100");
//        productAttributes.put("warranty_type", "Local Manufacturer Warranty");
//        productAttributes.put("name_ms","mala");
//        productAttributes.put("brand","Samsung");
//        productAttributes.put("price",400);
//        productAttributes.put("package_content","包装内容");
//        productAttributes.put("package_weight",100);// 重量
//        productAttributes.put("package_length",100);// 长
//        productAttributes.put("package_width",100);// 宽
//        productAttributes.put("package_height",100);// 高
//        productAttributes.put("tax_class","default");// 高

//        Map<String, Object> skus = new HashMap<String, Object>();
//        skus.put("package_content","电池、手机");
//        skus.put("package_length","220");
//        skus.put("package_height","200");
//        skus.put("package_width","210");
//        skus.put("package_weight","2");
//        skus.put("quantity","3");
//        skus.put("special_price","1000");
//        skus.put("price","800");
//        skus.put("available","1");
//        skus.put("_compatible_variation","Black 128GB");
//            skus.put("tax_class","default");
//        skus.put("display_size_mobile","20");
//        skus.put("storage_capacity_new","red");
//        skus.put("SellerSku","red");
//        skus.put("warranty","6");
//        skus.put("model","S3");
//        skus.put("special_from_date","2017-12-05");
//        skus.put("color_family","red");
//        skus.put("special_price","190");

//        skusAttributes.add(skus);
//        ProductBody body = new ProductBody();
//        body.setPrimaryCategory(categoryId);
//        body.setAttributes(productAttributes);
//        body.setSkus(skusAttributes);
//
//
//        CreateProduct createProduct = new CreateProduct(body);
//        try {
//            ModifyProductResponse response = createProduct.execute();
//            System.out.println("rvalue:"+response.getBody());
//        } catch (LazadaException e) {
//            System.out.println(e.getResponseStr());
//        }

//        "_compatible_variation": "Black 128GB",
//                "available": 1,
//                "fulfillmentBySellable": false,
//                "images": [
//        "https://my-staging.slatic.net/original/d314d340fbd85dc978e8c289a50a5ce2.jpg",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                ""
//        ],
//        "packageLength": "20",
//                "packageWeight": "2",
//                "package_content": "<p>电池、手机、数据线、耳机</p>\n",
//                "package_height": "5",
//                "package_width": "18",
//                "pendingStock": 0,
//                "price": 1180,
//                "product_measures": "",
//                "product_weight": "",
//                "quantity": 1,
//                "realTimeStock": 0,
//                "reservedStock": 0,
//                "rtsStock": 0,
//                "sellerSku": "red",
//                "shopSku": "",
//                "size": "",
//                "special_price": 1000,
//                "status": "active",
//                "url": ""

//
//        {
//            "Field": "Product",
//                "Message": "${TARGET}: Can't create sku without sku attribute, please select one at least"
//        },
//        {
//            "Field": "tax_class",
//                "Message": "Value is required and can not be empty",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "display_size_mobile",
//                "Message": "Value is required and can not be empty"
//        },
//        {
//            "Field": "storage_capacity_new",
//                "Message": "storage_capacity_new is mandatory, please select one at least",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "SellerSku",
//                "Message": "Value is required and can not be empty",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "package_weight",
//                "Message": "Value is required and can not be empty",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "warranty",
//                "Message": "Warranty period must be specified"
//        },
//        {
//            "Field": "package_length",
//                "Message": "Value is required and can not be empty",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "model",
//                "Message": "Value is required and can not be empty"
//        },
//        {
//            "Field": "special_from_date",
//                "Message": "The promoted start date is mandatory",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "color_family",
//                "Message": "color_family is mandatory, please select one at least",
//                "SellerSku": "null"
//        },
//        {
//            "Field": "special_price",
//                "Message": "The promoted price is greater than price",
//                "SellerSku": "null"
//        }

        //    ), Skus=[Sku(Status=active, quantity=1, product_weight=null, _compatible_variation=Black 128GB,
        // SellerSku=red, ShopSku=null, package_content=<p>电池、手机、数据线、耳机</p>
//            , Url=null, package_width=18, product_measures=null, package_height=5, size=null,
// special_price=1000.0, price=1180.0, packageLength=20, packageWeight=2, available=1,
// RealTimeStock=null, PendingStock=null, ReservedStock=null, RtsStock=null,
// FulfillmentBySellable=null, images=[https://my-staging.slatic.net/original/d314d340fbd85dc978e8c289a50a5ce2.jpg, , , , , , , ])])]



//        name;text;normal;[]
//        short_description;richText;normal;[]
//        SellerSku;text;sku;[]
//        warranty_type;singleSelect;normal;[AttributeOption(name=Local Manufacturer Warranty), AttributeOption(name=International Manufacturer Warranty), AttributeOption(name=Local Supplier Warranty), AttributeOption(name=No Warranty), AttributeOption(name=International Seller Warranty)]
//        name_ms;text;normal;[]
//        brand;singleSelect;normal;[]
//        price;numeric;sku;[]
//        package_content;richText;sku;[]
//        package_weight;numeric;sku;[]
//        package_length;numeric;sku;[]
//        package_width;numeric;sku;[]
//        package_height;numeric;sku;[]
//        tax_class;singleSelect;sku;[AttributeOption(name=tax 6), AttributeOption(name=default)]

//



//        {"ErrorResponse":{"Head":{"ErrorCode":500,"ErrorMessage":"E500: Create product failed","ErrorType":"Platform","RequestAction":"CreateProduct","RequestId":"0a15305215124001203756791e"},"Body":{"Errors":[{"Field":"Product","Message":"${TARGET}: Can't create sku without sku attribute, please select one at least"},{"Field":"tax_class","Message":"Value is required and can not be empty","SellerSku":"null"},{"Field":"display_size_mobile","Message":"Value is required and can not be empty"},{"Field":"storage_capacity_new","Message":"storage_capacity_new is mandatory, please select one at least","SellerSku":"null"},{"Field":"SellerSku","Message":"Value is required and can not be empty","SellerSku":"null"},{"Field":"package_weight","Message":"Value is required and can not be empty","SellerSku":"null"},{"Field":"warranty","Message":"Warranty period must be specified"},{"Field":"package_length","Message":"Value is required and can not be empty","SellerSku":"null"},{"Field":"model","Message":"Value is required and can not be empty"},{"Field":"special_from_date","Message":"The promoted start date is mandatory","SellerSku":"null"},{"Field":"color_family","Message":"color_family is mandatory, please select one at least","SellerSku":"null"},{"Field":"special_price","Message":"The promoted price is greater than price","SellerSku":"null"}]}}}

    }


//    [Product(PrimaryCategory=3, Attributes=Attributes(name=sanxingphone, short_description=<p>多快好省</p>
//            , video=null, brand=Samsung, model=note4, product_warranty=<p>3到111</p>
//            , name_ms=sanxing, description_ms=<p>good phono</p>
//    ), Skus=[Sku(Status=active, quantity=1, product_weight=null, _compatible_variation=Black 128GB, SellerSku=red, ShopSku=null, package_content=<p>电池、手机、数据线、耳机</p>
//            , Url=null, package_width=18, product_measures=null, package_height=5, size=null, special_price=1000.0, price=1180.0, packageLength=20, packageWeight=2, available=1, RealTimeStock=null, PendingStock=null, ReservedStock=null, RtsStock=null, FulfillmentBySellable=null, images=[https://my-staging.slatic.net/original/d314d340fbd85dc978e8c289a50a5ce2.jpg, , , , , , , ])])]


//    Baby & Toddler:9981;
//    Cameras:240;
//    Computers & Laptops:54;
//    Fashion:1819;
//    Groceries:3752;
//    Health & Beauty:1438;
//    Home & Living:411;
//    Home Appliances:275;
//    Media, Music & Books:3374;
//    Mobiles & Tablets:2;
//    Motors:3214;
//    Pet Supplies:11925;
//    Sample:10000278;
//    Sports & Outdoors:1970;
//    TV, Audio / Video, Gaming & Wearables:147;
//    Test Staging category:10000275;
//    Toys & Games:10168;
//    Travel & Luggage:1902;
//    Vouchers and Services:7430;
//    Watches Sunglasses Jewellery:8627;

}
