SELECT co.* FROM object_cart.cart c, object_cart.cart_object co 
where c.name in ('formCart', 'formCartV2', 'formDisplayCart', 'formDisplayCart2')
and c.id = co.cart_id;

DELETE from cart_object using cart_object
JOIN cart ON cart.id = cart_object.cart_id
AND cart.name in ('formCart', 'formCartV2', 'formDisplayCart', 'formDisplayCart2');


SELECT * FROM object_cart.cart where name in ('formCart', 'formCartV2', 'formDisplayCart', 'formDisplayCart2');

delete FROM object_cart.cart where name in ('formCart', 'formCartV2', 'formDisplayCart', 'formDisplayCart2');

commit;
