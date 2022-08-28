package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

class NormalItemTest {

	@Test
	public void sellInDateDecreases_QualityCannotBeNegative() {
		Item testItem = new Item("test", 0, 0);
		Item expectedItem = new Item("test", -1, 0);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void qualityDecrease() {
		Item testItem = new Item("test", 10, 10);
		Item expectedItem = new Item("test", 9, 9);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void qualityDecreaseAfterExpiry() {
		Item testItem = new Item("test", 0, 10);
		Item expectedItem = new Item("test", -1, 8);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}

}
