fn main() {
    let mut x = 5;
    println!("The value of x is: {}", x);
    x = 6;
    println!("The value of x is: {}", x);
    
    let _x = 2.0;
    let _y : f32 = 3.0;

    let _sum = 5 + 10;
    let _difference = 95.5 - 4.3;
    let _product = 4 * 30;
    let _quotient = 56.7 / 32.2;
    let _remainder = 43 % 5;


    let _t = true;
    let _f : bool = false;


    let _c = 'z';
    let _z = ' ';
    let _heart_eyed_cat = ' ';

    let tup: (i32, f64, u8) = (500, 6.4, 1);
    let (x, y , z) = tup;
    println!("The value of y is: {}", y);
    let five_hundred = tup.0;
    let six_point_four = tup.1;
    let one = tup.2;    
    
    let arr = [1, 2, 3, 4, 500];

    let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        
    let june = months[5];

    another_function();

}

fn another_function(){
    println!("Another function.");
}
