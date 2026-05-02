import Customer from "./entity/customer";
import Address from "./entity/address";
import OrderItem from "./entity/order_item";
import Order from "./entity/order";

let customer = new Customer("123", "Marcus");
let address = new Address("Sesame Street", 2, "1234","Manaus");
customer.Address = address;
customer.activate();

const item1 = new OrderItem("1","Item 1", 1, "1", 11);
const item2 = new OrderItem("2","Item 2", 2, "2", 15);
const order = new Order("1", "123", [item1, item2])