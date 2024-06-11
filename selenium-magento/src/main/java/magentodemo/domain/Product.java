package magentodemo.domain;

import lombok.Data;

import java.util.List;

@Data
public class Product {
	private String name;
	private String price;
	private List<String> sizes;
	private List<String> colors;
	private String selectedSize;
	private String selectedColor;
}
