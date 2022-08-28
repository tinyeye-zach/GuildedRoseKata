package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

class SulfurasTest {

	@Test
	public void sulfras_noChanges() {
		Item testItem = new Item("Sulfuras, Hand of Ragnaros", 100, 80);
		Item expectedItem = new Item("Sulfuras, Hand of Ragnaros", 100, 80);
		
		GildedRose app = new GildedRose(testItem);
		
		app.updateQuality();
		
		assertItemEquals(app.getItems()[0], expectedItem);
	}

}
