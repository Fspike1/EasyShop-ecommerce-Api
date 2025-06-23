package org.yearup.data;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryShoppingCartDao implements ShoppingCartDao{
    private Map<Integer, ShoppingCart> carts = new HashMap<>();

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        // if the user has no cart yet, create one
        if (!carts.containsKey(userId)) {
            carts.put(userId, new ShoppingCart());
        }

        return carts.get(userId);
    }
}

