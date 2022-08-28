package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

class ConjuredItemTest {

	@Test
	public void sellInDateDecreases_QualityCannotBeNegative() {
		Item testItem = new Item("Conjured Mana Cake", 0, 0);
		Item expectedItem = new Item("Conjured Mana Cake", -1, 0);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void sellInDateDecreases_QualityMinimume() {
		Item testItem = new Item("Conjured Mana Cake", 0, 1);
		Item expectedItem = new Item("Conjured Mana Cake", -1, 0);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void qualityDecrease() {
		Item testItem = new Item("Conjured Mana Cake", 10, 10);
		Item expectedItem = new Item("Conjured Mana Cake", 9, 8);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void qualityDecreaseAfterExpiry() {
		Item testItem = new Item("Conjured Mana Cake", 0, 10);
		Item expectedItem = new Item("Conjured Mana Cake", -1, 6);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}

}
