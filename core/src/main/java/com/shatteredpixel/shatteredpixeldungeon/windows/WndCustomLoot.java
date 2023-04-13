package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.*;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.*;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.*;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.*;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.*;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.*;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.*;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class WndCustomLoot extends Window {

    private static final int WIDTH		= 120;
    private static final int TTL_HEIGHT = 16;
    private static final int BTN_HEIGHT = 16;
    private static final int GAP        = 1;
    private static final int ICON_COLS  = 4;

    private ArrayList<LootCategory> lootCategories;
    private ArrayList<RedButton> buttons;
    private Map<String, Integer> customLoot;

    public WndCustomLoot() {
        super();

        RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 12 );
        title.hardlight(TITLE_COLOR);
        title.setPos(
                (WIDTH - title.width()) / 2,
                (TTL_HEIGHT - title.height()) / 2
        );
        PixelScene.align(title);
        add(title);

        // Armor
        // Weapons
        // Artifacts
        // Wands
        // Rings
        // Thrown Items
        // Potions
        // Scrolls
        // Food
        // Seeds
        // Misc

        lootCategories = new ArrayList<>();
        buttons = new ArrayList<>();
        customLoot = SPDSettings.customLoot();

        Class<?>[] weapons = combineLists(
                Generator.Category.WEP_T1.classes,
                Generator.Category.WEP_T2.classes,
                Generator.Category.WEP_T3.classes,
                Generator.Category.WEP_T4.classes,
                Generator.Category.WEP_T5.classes
        );

        lootCategories.add(new LootCategory("Weapons", false, false, weapons));

        lootCategories.add(new LootCategory("Armor", false,false,  new Class<?>[]{
                ClothArmor.class,
                LeatherArmor.class,
                MailArmor.class,
                ScaleArmor.class,
                PlateArmor.class,
        }));

        lootCategories.add(new LootCategory("Artifacts", false,false,  Generator.Category.ARTIFACT.classes));
        lootCategories.add(new LootCategory("Rings", false,true,  Generator.Category.RING.classes));
        lootCategories.add(new LootCategory("Wands", false,false,  Generator.Category.WAND.classes));

        Class<?>[] missiles = combineLists(
                Generator.Category.MIS_T1.classes,
                Generator.Category.MIS_T2.classes,
                Generator.Category.MIS_T3.classes,
                Generator.Category.MIS_T4.classes,
                Generator.Category.MIS_T5.classes
        );
        lootCategories.add(new LootCategory("Throwables", true,false,  missiles));

        Class<?>[] exoticPotions = new Class<?>[]{
                PotionOfShielding.class,
                PotionOfCorrosiveGas.class,
                PotionOfMastery.class,
                PotionOfSnapFreeze.class,
                PotionOfStamina.class,
                PotionOfDragonsBreath.class,
                PotionOfShroudingFog.class,
                PotionOfMagicalSight.class,
                PotionOfStormClouds.class,
                PotionOfDivineInspiration.class,
                PotionOfCleansing.class,
                PotionOfEarthenArmor.class,
        };
        Class<?>[] potions = combineLists(
                Generator.Category.POTION.classes,
                exoticPotions
        );

        lootCategories.add(new LootCategory("Potions", true,true,  potions));

        Class<?>[] exoticScrolls = new Class<?>[]{
                ScrollOfDivination.class,
                ScrollOfEnchantment.class,
                ScrollOfAntiMagic.class,
                ScrollOfSirensSong.class,
                ScrollOfChallenge.class,
                ScrollOfDread.class,
                ScrollOfMysticalEnergy.class,
                ScrollOfForesight.class,
                ScrollOfPassage.class,
                ScrollOfPsionicBlast.class,
                ScrollOfPrismaticImage.class,
                ScrollOfMetamorphosis.class
        };
        Class<?>[] scrolls = combineLists(
                Generator.Category.SCROLL.classes,
                exoticScrolls
        );

        lootCategories.add(new LootCategory("Scrolls", true,true,  scrolls));

        lootCategories.add(new LootCategory("Stones", true, false, Generator.Category.STONE.classes));

        Class<?>[] alchemy = new Class<?>[]{
                Alchemize.class,
                BeaconOfReturning.class,
                FeatherFall.class,
                SummonElemental.class,
                ArcaneCatalyst.class,
                AlchemicalCatalyst.class,
                GooBlob.class,
                MetalShard.class
        };

        lootCategories.add(new LootCategory("Alchemy", true, false, alchemy));

        Class<?>[] meatPie = new Class<?>[]{MeatPie.class};
        Class<?>[] food = combineLists(
                meatPie,
                Generator.Category.FOOD.classes
        );

        lootCategories.add(new LootCategory("Food", true,false,  food));

        lootCategories.add(new LootCategory("Seeds", true,false,  Generator.Category.SEED.classes));

        lootCategories.add(new LootCategory("Misc.", false,false,  new Class<?>[]{
                ScrollHolder.class,
                PotionBandolier.class,
                MagicalHolster.class,
                Ankh.class,
                CorpseDust.class,
                TengusMask.class,
                KingsCrown.class
        }));

        float pos = TTL_HEIGHT;
        for(LootCategory lootCategory : lootCategories) {
            RedButton categoryButton = new RedButton(lootCategory.titleWithCount()) {
                @Override
                protected void onClick() {
                    RenderedTextBlock text = this.text;

                    ShatteredPixelDungeon.scene().addToFront(new WndLootSelect(lootCategory) {
                        public void onBackPressed() {
                            super.onBackPressed();
                            text.text(lootCategory.titleWithCount());
                            layout();
                        }
                    });
                }
            };
            pos += GAP;
            categoryButton.setRect(0, pos, WIDTH, BTN_HEIGHT);

            add(categoryButton);
            buttons.add(categoryButton);

            pos = categoryButton.bottom();
        }

        resize(WIDTH, (int)pos);
    }

    private Class<?>[] combineLists(Class<?>[] ... classLists) {
        if (classLists.length == 0) {
            return new Class<?>[]{};
        }

        int totalLength = 0;
        for (Class<?>[] classList : classLists) {
            totalLength += classList.length;
        }

        Class<?>[] result = Arrays.copyOf(classLists[0], totalLength);

        int pos = classLists[0].length;
        for (int i = 1; i < classLists.length; i++) {
            Class<?>[] classList = classLists[i];
            System.arraycopy(classList, 0, result, pos, classList.length);
            pos += classList.length;
        }

        return result;
    }

    private int getLootCount(Class<Item> lootClass) {
        String classname = lootClass.getName();
        Integer count = customLoot.get(classname);
        if (count == null) {
            count = 0;
        }

        return count;
    }

    private void setLootCount(Class<Item> lootClass, int count) {
        String classname = lootClass.getName();

        if (count > 0) {
            customLoot.put(classname, count);
        } else {
            customLoot.remove(classname);
        }
    }

    private class LootCategory {
        private String title;
        private boolean isStackable;
        private boolean useIcon;
        private Class<?>[] classes;

        public LootCategory(String title, boolean isStackable, boolean useIcon, Class<?>[] classes) {
            this.title = title;
            this.isStackable = isStackable;
            this.useIcon = useIcon;
            this.classes = classes;
        }

        public String titleWithCount() {
            int numInUse = 0;
            for (Class<?> lootClass : classes) {
                if (getLootCount((Class<Item>) lootClass) >= 1) {
                    numInUse++;
                }
            }

            StringBuilder newText = new StringBuilder(title);
            if (numInUse >= 1) {
                newText.append(" (");
                newText.append(numInUse);
                newText.append(")");
            }

            return newText.toString();
        }
    }
    
    private class WndLootSelect extends Window {
        LootCategory lootCategory;
        
        public WndLootSelect(LootCategory lootCategory) {
            this.lootCategory = lootCategory;

            RenderedTextBlock title = PixelScene.renderTextBlock(lootCategory.title, 12);
            title.hardlight(TITLE_COLOR);
            title.setPos(
                    (WIDTH - title.width()) / 2,
                    (TTL_HEIGHT - title.height()) / 2
            );
            PixelScene.align(title);
            add(title);

            int buttonSize = WIDTH / ICON_COLS;
            
            float yPos = TTL_HEIGHT;
            float xPos = 0;
            for (int i = 0; i < lootCategory.classes.length; i++) {
                if (i > 0 && i % 4 == 0) {
                    yPos += buttonSize;
                }

                Class<Item> lootClass = (Class<Item>) lootCategory.classes[i];
                Item item;
                try {
                    item = lootClass.getConstructor(null).newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

                Image sprite;
                if (lootCategory.useIcon) {
                    sprite = new Image(Assets.Sprites.ITEM_ICONS);
                    sprite.frame(ItemSpriteSheet.Icons.film.get(item.icon));
                } else {
                    sprite = new ItemSprite(item.image);
                }

                if (getLootCount(lootClass) > 0) {
                    sprite.alpha(1);
                } else {
                    sprite.alpha(0.5f);
                }

                IconButton lootButton = new IconButton(sprite) {
                    @Override
                    protected void onClick() {
                        super.onClick();

                        Image icon = this.icon;

                        if (lootCategory.isStackable) {
                            ShatteredPixelDungeon.scene().addToFront(new WndLootCount(lootClass) {
                                public void onBackPressed() {
                                    super.onBackPressed();
                                    int newCount = getLootCount(lootClass);

                                    if (newCount >= 1) {
                                        setLootCount(lootClass, newCount);
                                        icon.alpha(1);
                                    } else {
                                        setLootCount(lootClass, 0);
                                        icon.alpha(0.5f);
                                    }
                                }
                            });
                        } else {
                            int currentCount = getLootCount(lootClass);

                            if (currentCount >= 1) {
                                setLootCount(lootClass, 0);
                                icon.alpha(0.5f);
                            } else {
                                setLootCount(lootClass, 1);
                                icon.alpha(1);
                            }
                        }
                    }
                };

                lootButton.setRect(xPos, yPos, buttonSize, buttonSize);
                add(lootButton);

                if (i % 4 < 3) {
                    xPos += buttonSize;
                } else {
                    xPos = 0;
                }
            }

            yPos += buttonSize;
            resize(WIDTH, (int)yPos);
        }

        @Override
        public void onBackPressed() {
            SPDSettings.customLoot(customLoot);
            super.onBackPressed();
        }
    }

    private class WndLootCount extends Window {
        Class<Item> lootClass;
        RenderedTextBlock countLabel;

        public WndLootCount(Class<Item> lootClass) {
            this.lootClass = lootClass;

            RenderedTextBlock title = PixelScene.renderTextBlock("Amount", 12);
            title.hardlight(TITLE_COLOR);
            title.setPos(
                    (WIDTH - title.width()) / 2,
                    (TTL_HEIGHT - title.height()) / 2
            );
            PixelScene.align(title);
            add(title);

            float buttonSize = WIDTH / 7f;

            RedButton set0 = new RedButton("0") {
                @Override
                protected void onClick() {
                    setLootCount(lootClass, 0);
                    updateCountLabel();
                }
            };
            set0.setRect(0, TTL_HEIGHT, buttonSize, buttonSize);
            add(set0);

            RedButton minus10 = new RedButton("-10") {
                @Override
                protected void onClick() {
                    int count = getLootCount(lootClass);
                    count -= 10;
                    if (count < 0) {
                        count = 0;
                    }
                    setLootCount(lootClass, count);
                    updateCountLabel();
                }
            };
            minus10.setRect(buttonSize, TTL_HEIGHT, buttonSize, buttonSize);
            add(minus10);

            RedButton minus1 = new RedButton("-1") {
                @Override
                protected void onClick() {
                    int count = getLootCount(lootClass);
                    count -= 1;
                    if (count < 0) {
                        count = 0;
                    }
                    setLootCount(lootClass, count);
                    updateCountLabel();
                }
            };
            minus1.setRect(buttonSize * 2, TTL_HEIGHT, buttonSize, buttonSize);
            add(minus1);

            countLabel = PixelScene.renderTextBlock(String.valueOf(getLootCount(lootClass)), 12);
//            countLabel.hardlight(TITLE_COLOR);
            countLabel.setPos(buttonSize * 3, TTL_HEIGHT);
//            PixelScene.align(countLabel);
            add(countLabel);

            RedButton plus1 = new RedButton("+1") {
                @Override
                protected void onClick() {
                    int count = getLootCount(lootClass);
                    count += 1;
                    if (count > 100) {
                        count = 100;
                    }
                    setLootCount(lootClass, count);
                    updateCountLabel();
                }
            };
            plus1.setRect(buttonSize * 4, TTL_HEIGHT, buttonSize, buttonSize);
            add(plus1);

            RedButton plus10 = new RedButton("+10") {
                @Override
                protected void onClick() {
                    int count = getLootCount(lootClass);
                    count += 10;
                    if (count > 100) {
                        count = 100;
                    }
                    setLootCount(lootClass, count);
                    updateCountLabel();
                }
            };
            plus10.setRect(buttonSize * 5, TTL_HEIGHT, buttonSize, buttonSize);
            add(plus10);

            RedButton set100 = new RedButton("100") {
                @Override
                protected void onClick() {
                    setLootCount(lootClass, 100);
                    updateCountLabel();
                }
            };
            set100.setRect(buttonSize * 6, TTL_HEIGHT, buttonSize, buttonSize);
            add(set100);

            resize(WIDTH, (int) (TTL_HEIGHT + buttonSize));
        }

        private void updateCountLabel() {
            countLabel.text(String.valueOf(getLootCount(lootClass)));
        }
    }
}
