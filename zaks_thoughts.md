# Coding Thoughts with Zak Knippel
In this document, I'll be writing down my thought process on how I came to the final answer. Please forgive the minor amounts of humour or conversational writing style - I've found it to be more engaging to both read and write, and helps both technical and non-technical readers understand and remember what's written. I also am a big fan of roleplaying games such as *Dungeons & Dragons* and can't help but play along with the fantasy theme of this kata. Got to have fun with these assignments, right?

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
   - Act the same as normal items, but lose twice the quality in the same time

If I were building this from scratch, my immediate thought would be to use the Factory design pattern and ensure that each item type handles its own quality-change rules. That would ensure that each item encapsulates its own rules and is not dependent on anything else to follow them. It would also allow us to make more types easily as the Guilded Rose grows, making it easier to maintain and grow from. *Unfortunately*, Cranky will insta-kill me if I do that and I'd prefer to live (and code) another day.

Given the chance, I would also ask for some clarity regarding if item types can stack in any way (e.g. can you have a Conjured+Appreciating item? Or a Conjured+Time-Senstive item?). If we're talking about classes being able to stack, then a Composite design pattern would solve the Conjured issue at the very least. However, it's a little weird to me that something like "Conjured Aged Brie" would increase in value faster than regular aged brie - that sounds like a recipe to crash the cheese market. For now, I'm going to assume that **Conjured** items are a separate type, and other than the steeper decrease in quality they follow the same rules as **Normal** items.
*So, that means tells mne that I need the following:
-- Seperate methods to update the **Quality** of a given item following the rules for each type
- A way to identify what *type* and item is so I can call the right method
- (OPTIONAL) A good book on the importance of shared code ownership to give Cranky

##
