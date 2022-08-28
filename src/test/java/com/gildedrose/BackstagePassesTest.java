package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

class BackstagePassesTest {

	@Test
	public void backstagePasses_qualityIncrease_Outside10Days() {
		Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 20, 1);
		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 19, 2);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	
	@Test
	public void backstagePasses_qualityIncrease_Inside10Days() {
		Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10);
		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 9, 12);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void backstagePasses_qualityIncrease_Inside5Days() {
		Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10);
		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 4, 13);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
	
	@Test
	public void backstagePasses_quality0_WhenExpired() {
		Item testItem = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 0);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}
}
