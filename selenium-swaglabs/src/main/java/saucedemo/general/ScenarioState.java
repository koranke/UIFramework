package saucedemo.general;

import lombok.Data;
import saucedemo.domain.Product;

import java.util.List;

@Data
public class ScenarioState {
	private List<Product> products;

}
