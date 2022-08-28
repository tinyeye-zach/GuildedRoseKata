# Coding Thoughts with Zak Knippel
In this section, I'll be writing down my thought process on how I came to the final answer. Please forgive the minor amounts of humour or conversational writing style - I've found it to be more engaging to both read and write, and helps both technical and non-technical readers understand and remember what's written. I also am a big fan of roleplaying games such as *Dungeons & Dragons* and can't help but play along with the fantasy theme of this kata. Got to have fun with these assignments, right?

## 1 - Understanding the Requirements
Okay, so here's the short version of the requirements:

- We have a list of items to be sold from the Guilded Rose Inn
  - Each item has a number of days they should be sold by (which is noted denoted as **SellIn**)
    - This value *can be negative*, indicating that an item is overdue for selling
    - This value will decrease every day that an item is not sold
  - At the end of each day that an item is not sold, it's **Quality** will change
    - **Quality** cannot increase above 50, or decrease below 0
    - Going from the code, the **__default__ Quality rate of change** is **1** (I will)
      - ***IMPORTANT NOTE:*** I will be denoting this rate of Quality change as ***dQ*** going forward
    - Once the item is overdue for sale (i.e. **SellIn < 0**), it's ***dQ*** is *doubled*
    - For ***Legendary*** items, ***dQ*** does not change, *but* it will start at a value greater than 50 (e.g. 80)
    - Certain types of items (i.e. the ***"Backstage Passes"***) will...
      - Have varying ***dQ*** values depending on the value of **SellIn**
      - Once **SellIn <= 0**, **Quality** is set to 0
    - ***Conjured*** items lose quality *double* the normal rate
- There is a goblin in the corner who *instant-kill me* if I touch the **Item** class
  - I have elected to name this goblin "Cranky" (just because I like that personal touch)
  - I also cannot touch the **Items** property in the **GildedRose** class
  - Being deceased is a major dampener on future coding opportunities, so I'm going to keep Cranky happy

## 2 - Initial Thoughts
Immediately, my initial thought is that we should break these items down into five types:

1. **Normal Items**
   - *Lose* 1 **Quality** every day (***dQ*** **= -1**)
   - **Quality** can *never* be above 50 or below 0
   - ***dQ*** is doubled once **SellIn** is negative
2. **Appreciating Items**
   - *Gain* 1 **Quality** every day (***dQ*** **= 1**)
   - **Quality** can *never* be above 50 or below 0
   - ***dQ*** is doubled once **SellIn** is negative
3. **Legendary Items**
   - Never change in **Quality** (***dQ*** **= 0**)
4. **Time-Senstive Items**
   - *Gain* **Quality** based on **SellIn** value
   - Once **SellIn = 0**, **Quality** is *automatically* set to 0
5. **Conjured Items**
   - Act the same as normal items, but lose *twice* the quality in the same time

If I were building this from scratch, my immediate thought would be to use the Factory design pattern and ensure that each item type handles its own quality-change rules. That would ensure that each item encapsulates its own rules and is not dependent on anything else to follow them. It would also allow us to make more types easily as the Guilded Rose grows, making it easier to maintain and grow from. *Unfortunately*, Cranky will insta-kill me if I do that and I'd prefer to live (and code) another day.

Given the chance, I would also ask for some clarity regarding if item types can stack in any way (e.g. can you have a Conjured+Appreciating item? Or a Conjured+Time-Senstive item?). If we're talking about classes being able to stack, then a Composite design pattern would solve the Conjured issue at the very least. However, it's a little weird to me that something like "Conjured Aged Brie" would increase in value faster than regular aged brie - that sounds like a recipe to crash the cheese market. For now, I'm going to assume that **Conjured** items are a separate type, and other than the steeper decrease in quality they follow the same rules as **Normal** items.

So, that means tells me that I need the following:
- Seperate methods to update the **Quality** of a given item following the rules for each *type*
- A way to identify what *type* and item is so I can call the right method
- (OPTIONAL) A good book on the importance of shared code ownership to give Cranky

## 3 - Relearning Java (because it's been a while) and Research

As you can see from my Initial Thoughts section, I had a few questions. This is a pretty famous Kata, so my immediate thought after reviewing the code and the requirements was "let's see how these requirements are commonly interpreted". So, I went off to start researching how others approached this problem and dust off my Java-related cobwebs. I also wanted to see if I could learn some better techniques for code-refactoring procedures, and extract the general patterns that other coders used.

I limited myself to these two videos I found discussing this particular problem:
- [Solving Gilded Rose Kata (No Nesting) by Craft vs Cruft](https://youtu.be/5oAs5Jr5njU)
  - This had some amazing unit tests and a great solution, but I felt the code readability could be a little better.
  - I noticed an error where the quality could change by 2 or more points, but not be set to the max/min if it goes out-of-bounds (e.g. Quality 1, decrease by 2, should result in Quality 0 - but I don't believe his solution would do that)
  - I also was not a fan of having to continuously "tick" down the quality 1 point at a time
- [RailsConf 2014 - All the Little Things by Sandi Metz](https://youtu.be/8bZh5LMaSmE)
  - This one did not have the same requirements regarding Cranky the goblin as the assignment, but it *did* validate my previous theory regarding the Factory design pattern

## 4 - High-Level of How I Approached This
TO BEGIN... that is *waaaaay* too many nested if statements. Makes reading this a massive pain.

I also noticed that each item tends to "tick" - having it's quality changed by 1 point at a time, with larger changes simply looping through multiple times. And each time we do a change, there is a guard that checks if the quality will stay within the accepted range and should be changed in the first place. That's coming up a lot, so that's a great place to take things out!

To make testing a little easier, I also decided to switch the Gilded Rose constructor up so it can take a single Item object or an array - just to make testing easier. I 100% agree with Craft vs Cruft's suggested change there.

I also dislike hard-coding strings like the old code does, so that needs to be changed. I've turned them into constants and put them at the top of the Gilded Rose class.

So, here's the steps...
1. Create a function to handle Quality changes (***changeQuality()***). It should...
   - Ensure that any item's Quality remains within the expected range after the change
   - Handle setting the item to min/max values if the change rate is too high (edge-case)
2. Create a function to check if an item is Expired (***isItemExpired()***)
   - This isn't entirely necessary - but I find it easier to reference a single function than risk duplicate code here
4. Create a function for each *type* of item that updates the item's **Quality** following the proper rules
   1. Normal items - ***updateNormalItems()***
   2. Appreciating items - ***updateAppreciatingItem()***
   3. Legendary items - ***updateLegendaryItem()*** (this is not really needed, but I felt it was good for code readability and possible expansion later)
   4. Time-sensitive items - ***updateTimeSensitiveItem()***
   5. Conjured items - ***updateConjuredItem()***
5. Create a function to identify the *type* of item (based off it's name) and call the appropriate update function (***updateItemQuality()***)
6. Have the ***updateQuality()*** function loop through the list of items and call ***updateItemQuality()***
7. Improve the GildedRose constructor so it can handle a single item or array of items (I really agree with Craft vs Cruft on this! Makes it far easier for unit-testing)

And that's it!

## 5 - Quick Thoughts on How I Would Expand on This
- Honestly, the rules for how to change item quality should be encapsulated in the Item class. Again, a factory design pattern would be *perfect* for this!
- I'm limiting myself to the specific names given in the test fixture, and don't like that
  - I was struggling to find a great way to make a switch statement that was based off identifying keywords in the name
  - *Again, how do we handle compound types? Is that a thing in this problem?*
- ***isItemExpired()*** should also be moved into the Item class - a solid point to check for other possible causes that an item could expire beyond it's sellIn value
- Currently, time-sensitive items only follow the rules given for backstage passes and that leaves me concerned for future expansions
  - It would be great to allow other patterns of quality changed based on days remaining
- Switch statements are a pain to maintain, so moving all this quality-changing object into each item would be significantly easier to expand upon going forward
