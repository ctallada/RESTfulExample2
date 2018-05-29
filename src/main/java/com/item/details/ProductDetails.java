package com.item.details;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mkyong.Bidder;
import com.mkyong.BidderComparator;
import com.mkyong.Product;
import com.mkyong.ProductsList;

@Path("/market")
public class ProductDetails {

	static List<Product> productsList = new ArrayList<Product>();
	static List<Product> defaultProductsList = new ArrayList<Product>();
	static List<Bidder> biddersList = new ArrayList<Bidder>();
	static int productId=23000;
	static int bidId=1100;
	static boolean defaultProductsFlag=true;
	static boolean defaultBiddersFlag=true;
	static ProductsList prductsListObject = new ProductsList();
	/*@GET
	@Path("/items/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductDetails() {

		if(defaultProductsFlag){
			getDefaultProducts();
		}

		System.out.println("Product Details List:::: "+productsList+"\n");
		return productsList;

	}*/
	

	@GET
	@Path("/items/reset")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductsList resetItems() {
		
		defaultProductsFlag = true;
		if(defaultProductsFlag){
			productId=23000;
			getDefaultProducts();
		}

		List<Product> productList = prductsListObject.getProducts();
		System.out.println("Product Details List size:::: "+productList.size()+"\n");
		return prductsListObject;

	}

	
	
	@GET
	@Path("/items/list")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductsList getProductDetails(@QueryParam("id") String idsList) {

		System.out.println("addProductDetails defaultProductsFlag:: " + defaultProductsFlag);
		if (defaultProductsFlag) {
			getDefaultProducts();
		}

		System.out.println("Product Details List:::: " + prductsListObject + "\n");

		List<Product> productList = prductsListObject.getProducts();
		System.out.println("Product Details List size:::: " + productList.size() + "\n");
		System.out.println("Query Parameter Value:::: idsList=="+idsList);
		if (idsList != null) {
			ProductsList queryParametersProductsList = new ProductsList();
			List<Product> queryProductsList = new ArrayList<Product>();
			String[] ids = idsList.split(",");
			for (String string : ids) {
				Integer id = Integer.parseInt(string);
				if (id > 0) {
					for (Product product : productList) {
						if (id.equals(product.getId())) {
							queryProductsList.add(product);
						}
					}

				}
			}
			queryParametersProductsList.setProducts(queryProductsList);
			return queryParametersProductsList;
		}
		return prductsListObject;

	}
	
	
	
	@DELETE
	@Path("/items/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response  deleteProduct(@PathParam("id") Integer id) throws Exception {
		boolean removeFlag = false;
		List<Product> productList = prductsListObject.getProducts();
		CopyOnWriteArrayList<Product> copyList = new CopyOnWriteArrayList<Product>();
		copyList.addAll(productList);
		for (Product product : copyList) {
			if(id.equals(product.getId())){
				copyList.remove(product);
				removeFlag = true;
			}
		}
		
		if(removeFlag){
			productList = new ArrayList<Product>();
			productList.addAll(copyList);
			prductsListObject.setProducts(productList);
			System.out.println("After removing the products list:::\n "+prductsListObject);
			return Response.noContent().build();
        } else {
            throw new Exception("Element Does Not Exist");
        }
		
		
	}
	
	@POST
	@Path("/item/info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Product addProductDetails(Product product) throws Exception {

		boolean productFlag = true;
		if(defaultProductsFlag){
			getDefaultProducts();
		}
		
		if (product != null) {
			productsList = prductsListObject.getProducts();
			String type = product.getType();
			String brand = product.getBrand();
			Double price = product.getPrice();
			if (type != null && brand != null && price > 0) {
				for (Product defaultProduct : productsList) {
					if (type.equals(defaultProduct.getType()) && brand.equals(defaultProduct.getBrand())) {
						productFlag = false;
						break;
					}
				}
			} else {
				throw new Exception("INVALID_INPUT");
			}

			if (productFlag) {
				productId = productId + 1;
				product.setId(productId);
				productsList.add(product);
				prductsListObject.setProducts(productsList);
				System.out.println("Product added successfully:::: "+ productFlag);
			} else {
				throw new Exception("PRODUCT_ALREADY_EXISTS");
			}
		}

		return product;
	}

	
	@PUT
	@Path("/item/info/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Product updateProductDetails(Product product) throws Exception {
		productsList = prductsListObject.getProducts();
		Product actulProduct = null;
		boolean updateProductFlag = false;
		if(product != null){
			Integer id = product.getId();
			if(id != null && id > 0){
				for (Product product1 : productsList) {
					if(id.equals(product1.getId())){
						updateProductFlag = true;
						actulProduct = product1;
						break;
					}
					
				}
			}
			if(updateProductFlag){
				productsList.remove(actulProduct);
				productsList.add(product);
				prductsListObject.setProducts(productsList);
			}
		}
		System.out.println("updateProductDetails ending........");
		return product;
	}

	
	
	@GET
	@Path("/bidders/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bidder> getBiddersDetails() {

		if(defaultBiddersFlag){
			getDefaultBids();
		}

		System.out.println("Bidders Details List:::: "+biddersList+"\n");

		return biddersList;

	}

	
	@POST
	@Path("/bidder/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Bidder addBidderDetails(Bidder bid) {

		if (bid != null) {
			bid.setId(bidId++);
		}
		biddersList.add(bid);
		return bid;

	}
	
	@GET
	@Path("/{brand}/bidders/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bidder> getProductBiddersDetails(
			@PathParam("brand") String brand) {
		List<Bidder> brandBiddersList = new ArrayList<Bidder>();
		if (brand != null && brand.length() > 0) {
			if (biddersList.size()==0 || productsList.size()==0) {
				getDefaultProducts();
				getDefaultBids();
			}
			for (Bidder bidder : biddersList) {
				if (bidder.getBrand().equals(brand))
					brandBiddersList.add(bidder);
			}

		}
		Collections.sort(brandBiddersList, new BidderComparator());
		return brandBiddersList;
	}

	
	private Product getProduct(String type, double price, String description, String brand) {

		Product product = new Product();
		productId = productId + 1;
		product.setId(productId);
		product.setType(type);
		product.setPrice(price);
		product.setBrand(brand);
		product.setDescription(description);
		
		return product;
	}
	
	private Bidder getBidder(int id, String name, String type, String brand, double quote) {

		Bidder bid = new Bidder();

		bid.setId(id);
		bid.setName(name);
		bid.setType(type);
		bid.setQuote(quote);
		bid.setBrand(brand);
		return bid;
	}
	
	private List<Product> getDefaultProducts(){
		
		Product product1 = getProduct("SHIRT", 851, "Mens Wear Dresses", "PETER_ENGLAND");
		Product product2 = getProduct("KURTI", 899, "Womens Wear Dresses", "ALLEND_SOLEY");
		if(defaultProductsFlag){
			defaultProductsList = new ArrayList<Product>();
			defaultProductsList.add(product1);
			defaultProductsList.add(product2);
			defaultProductsFlag=false;
			productsList.addAll(defaultProductsList);
			prductsListObject.setProducts(defaultProductsList);
			System.out.println("prductsListObject::::: "+prductsListObject);
		}
		
		System.out.println("Defailt Product Details List:::: "+defaultProductsList+"\n");
		return defaultProductsList;
	}
	
	private List<Bidder> getDefaultBids(){
		bidId++;
		Bidder bidder1 = getBidder(bidId++, "Venkat", "SHIRT", "PETER_ENGLAND", 855);
		Bidder bidder2 = getBidder(bidId++, "Sathvik", "SHIRT", "PETER_ENGLAND", 870);
		Bidder bidder3 = getBidder(bidId++, "Sahasra", "KURTI", "ALLEND_SOLEY", 950);
		Bidder bidder4 = getBidder(bidId++, "Laxmi", "KURTI", "ALLEND_SOLEY", 925);
		if(defaultBiddersFlag){
			biddersList.add(bidder1);
			biddersList.add(bidder2);
			biddersList.add(bidder3);
			biddersList.add(bidder4);
			defaultBiddersFlag = false;
		}
		
		System.out.println("Defailt Bid Details List:::: "+biddersList+"\n");
		return biddersList;
		
	}
	

}
