//here are some comments
fn main() {
    println!("Hello, world!");
    another_function(16, 64);
    let x = five();
    println!("The value of x is: {}", x);
}

fn another_function(x : i32, y: i32){
    println!("The value of x is: {}", x);

    println!("The value of y is: {}", y);
}

fn five() -> i32 {
    5
}
