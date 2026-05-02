use std::io;

fn main() {
    temperature_converter();
}

fn fahrenheight_to_celsius(fahrenheight : f32) -> f32{
    (fahrenheight - 32.0) * 5.0 / 9.0
}
fn celsius_to_fahrenheight(celsius : f32) -> f32 {
    (celsius * 9.0 / 5.0) + 32.0 
}
fn temperature_converter(){
    
    //loop as long as they don´t insert a valid option
    let choice = loop {
        println!("Select your choice of conversion:");
        println!("From Celsius to Fahrenheight (1)");
        println!("From Fahrenheight to Celsius (2)");
       
        
        let mut choice = String::new();
        //input and error handling
        io::stdin().read_line(&mut choice)
            .expect("Failed to read line");
        let choice: u32 = match choice.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };
        //if guess is valid, break 
        if choice == 1 || choice == 2 {
            if choice == 1 {
                println!("So, you´ve chosen Celsius to Fahrenheight conversion");               
            } else {
                println!("So, you've chosen Fahrenheight to Celsius conversion");
            }
            break choice;
        }
        
    };
    let temperature = loop {
        if choice == 1 {
            println!("Input your temperature in Celsius: ");
        } else {
            println!("Input your temperature in Fahrenheight");
        }

        let mut temperature = String::new();
        io::stdin().read_line(&mut temperature)
            .expect("Failed to read line");
        let temperature: f32 = match temperature.trim().parse() {
            Ok(num) => break num,
            Err(_) => continue,
        };
    };

    if choice == 1 {
        let temperature_converted = celsius_to_fahrenheight(temperature);    
        println!("This is your temperature in Fahrenheight: {}", temperature_converted);
    }
    
    if choice == 2 {
        let temperature_converted = fahrenheight_to_celsius(temperature);
        println!("This is your temperature in Celsius: {}", temperature_converted);
    }
}

