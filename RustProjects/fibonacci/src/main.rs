use std::io;

fn main() {
    
    let fibs = loop {
        println!("Chose a number in the fibonacci sequence");
        let mut fibs = String::new();
        io::stdin().read_line(&mut fibs)
            .expect("Failed to read line");
        let fibs: u32 = match fibs.trim().parse() {
            Ok(num) => break num,
            Err(_) => continue,
        };
    };
    let fibonacci = fibonacci_recursive(fibs); 
    println!("This is your number: {}", fibonacci);

}
fn fibonacci_recursive(n: u32) -> u32 {
    if n == 0 {
        return 0;
    } else if n == 1 {
        return 1;
    } else {
        return fibonacci_recursive(n - 1) + fibonacci_recursive(n - 2);
    }
}
