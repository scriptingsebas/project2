package org.turnbasedtitans.project2.dao;

import org.turnbasedtitans.project2.model.Item;

import java.util.List;

/**
 *  * Data access operations for Item entities.
 *
 * @author Sebastian Guillen
 * @since 4/20/26
 * @version 0.1.0
 */
public interface ItemDAO {
    void insertItem(Item item);

    void updateItem(Item item);

    void deleteItem(int itemId);

    Item getItemById(int itemId);

    List<Item> getItemsByName(String name);
}
