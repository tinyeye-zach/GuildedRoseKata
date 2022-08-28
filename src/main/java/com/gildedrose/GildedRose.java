package com.gildedrose;

class GildedRose {
	// CONSTANTS
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String CONJURED_CAKE = "Conjured Mana Cake";
    
    Item[] items;
    
    
    public GildedRose(Item... items) {
        this.items = items;
    }
    
    public Item[] getItems() {
    	return items;
    }

    
    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }
    
    private void updateNormalItem(Item item) {
    	changeQuality(item, -1);
    	item.sellIn--;
    	if (isItemExpired(item)) changeQuality(item, -1);
    }
    
    private void updateAppreciatingItem(Item item) {
    	changeQuality(item, 1);
    	item.sellIn--;
    	if (isItemExpired(item)) changeQuality(item, 1);
    }
    
    private void updateLegendaryItem(Item item) {
    	// DO NOTHING!!!
    }
    
    private void updateTimeSensitiveItem(Item item) {
    	if (item.sellIn <= 5) {
    		changeQuality(item, 3);
    	} else if (item.sellIn <= 10) {
    		changeQuality(item, 2);
    	} else {
    		changeQuality(item, 1);
    	}
    	item.sellIn--;
    	if (isItemExpired(item)) changeQuality(item, -(item.quality));
    }
    
    private void updateConjuredItem(Item item) {
    	changeQuality(item, -2);
    	item.sellIn--;
    	if (isItemExpired(item)) changeQuality(item, -2);
    }
    
    private void changeQuality(Item item, int rate) {
        // Temporarily store the supposed new quality of the item
    	int newQuality = item.quality + rate;
        
        // If the item quality is valid, set the item's quality to the new quality
        boolean inValidRange = (newQuality <= 50) && (newQuality >= 0);
        if (!inValidRange) {	// This follows D.R.Y. better, but I don't think it's as readable
        	newQuality = (newQuality > 50) ? 50 : 0; 	// If the new quality is out of the valid range, set it to the limit and assign it
        }
        
        item.quality = newQuality;
    }
    
    private boolean isItemExpired(Item item) {
    	return (item.sellIn < 0);
    }
    
    public void updateItemQuality(Item item) {
    	switch (item.name) {
    		case AGED_BRIE:
    			updateAppreciatingItem(item);
    			break;
    		case SULFURAS:
    			updateLegendaryItem(item);
    			break;
    		case BACKSTAGE_PASSES:
    			updateTimeSensitiveItem(item);
    			break;
    		case CONJURED_CAKE:
    			updateConjuredItem(item);
    			break;
    		default:
    			updateNormalItem(item);
    			
    	}
    }

}
