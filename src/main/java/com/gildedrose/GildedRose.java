package com.gildedrose;

class GildedRose {
	// CONSTANTS
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    
    Item[] items;
    
    
    public GildedRose(Item[] items) {
        this.items = items;
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
    
    private void updateBackstagePass(Item item) {
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
    
    private void changeQuality(Item item, int rate) {
        // Temporarily store the supposed new quality of the item
    	int newQuality = item.quality + rate;
        
        // If the item quality is valid, set the item's quality to the new quality
        boolean inValidRange = (newQuality <= 50) && (newQuality >= 0);
        if (inValidRange) {
            item.quality = newQuality;
        }
    }
    
    private boolean isItemExpired(Item item) {
    	return (item.sellIn < 0);
    }

    public void updateItemQuality(Item item) {
		if (!item.name.equals(AGED_BRIE)
                    && !item.name.equals(BACKSTAGE_PASSES)) {
                if (item.quality > 0) {
                    if (!item.name.equals(SULFURAS)) {
                        changeQuality(item, -1);
                    }
                }
            } else {
                if (item.quality < 50) {
                    changeQuality(item, 1);

                    if (item.name.equals(BACKSTAGE_PASSES)) {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                changeQuality(item, 1);
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                changeQuality(item, 1);
                            }
                        }
                    }
                }
            }

            if (!item.name.equals(SULFURAS)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!item.name.equals(AGED_BRIE)) {
                    if (!item.name.equals(BACKSTAGE_PASSES)) {
                        if (item.quality > 0) {
                            if (!item.name.equals(SULFURAS)) {
                                changeQuality(item, -1);
                            }
                        }
                    } else {
                        item.quality = item.quality - item.quality;
                        changeQuality(item, -(item.quality));
                    }
                } else {
                    if (item.quality < 50) {
                        changeQuality(item, 1);
                    }
                }
            }
    }
}
