package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

class AgedBrieTest {

	@Test
	public void agedBrie_IncreasesInQuality() {
		Item testItem = new Item("Aged Brie", 2, 2);
		Item expectedItem = new Item("Aged Brie", 1, 3);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void agedBrie_IncreasesInQuality_ExpiryDoublesRate() {
		Item testItem = new Item("Aged Brie", 0, 2);
		Item expectedItem = new Item("Aged Brie", -1, 4);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void agedBrie_50QualityMax() {
		Item testItem = new Item("Aged Brie", 10, 50);
		Item expectedItem = new Item("Aged Brie", 9, 50);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}

}
