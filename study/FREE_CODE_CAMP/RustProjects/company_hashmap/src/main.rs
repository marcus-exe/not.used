use std::io;
use std::collections::HashMap;


 #[derive(Eq, Hash, PartialEq)]
enum Function {
    Sales,
    Marketing, 
    Development
}

fn main() {
    
    let mut company : HashMap<Function, Vec<String>> = HashMap::new();
    let mut sales_vector : Vec<String> = Vec::new(); 
    let mut marketing_vector : Vec<String> = Vec::new();  
    let mut development_vector : Vec<String> = Vec::new(); 
    company.insert(Function::Sales, sales_vector);
    company.insert(Function::Marketing, marketing_vector);
    company.insert(Function::Development, development_vector);
    
    println!("Welcome to Systems Company");

    loop {
        println!("Type (1) to add a new Employee");
        println!("Type (2) to print Employees");
        println!("Type (3) to quit");
        let mut option = String::new();
        io::stdin().read_line(&mut option)
            .expect("Failed to read line");
        let option: u32 = match option.trim().parse() {
            Ok(num) => num,
            Err(_) => continue
        };
        if option == 1 || option == 2 || option == 3 {
            match option {
                1 => add_employee(&mut company),
                2 => print_employees(&mut company),
                3 => break,
                _ => continue
            }
        } 
    }
}

fn add_employee(hashmap: &mut HashMap<Function, Vec<String>>){

    let mut name = String::new();
    println!("What is the name of your employee?");
    io::stdin().read_line(&mut name)
        .expect("Failed to read line");
    let name = name.trim();

    let area = loop {
        let mut area = String::new();
        println!("Which area would you like to add {name}?");
        println!("(1) -> Sales");
        println!("(2) -> Marketing");
        println!("(3) -> Development");

        io::stdin().read_line(&mut area)
            .expect("Failed to read line");
       
        let area : u32  = match area.trim().parse() {
            Ok(num) => num,
            Err(_) => continue
        };
        
        if area == 1 || area == 2 || area == 3 {
            match area {
                1 => break Function::Sales,
                2 => break Function::Marketing,
                3 => break Function::Development,
                _ => continue
            }
        }
    };
    
    match area {
        Function::Sales => {
            if let Some(sales_vector) = hashmap.get_mut(&Function::Sales){
                sales_vector.push((*name).to_string());
            }
        }
        Function::Marketing => {
            if let Some(marketing_vector) = hashmap.get_mut(&Function::Marketing){
                marketing_vector.push((*name).to_string());
            }
        }
        Function::Development => {
            if let Some(development_vector) = hashmap.get_mut(&Function::Development){
                development_vector.push((*name).to_string());
            }
        }  

    }
}

fn print_employees(hashmap: &mut HashMap<Function, Vec<String>>){

    loop {
        println!("Would you like to print:");
        println!("(1) Sales employees");
        println!("(2) Marketing employees");     
        println!("(3) Development employees");     
        println!("(4) All employees");     
        
        let mut option = String::new();

        io::stdin().read_line(&mut option)
            .expect("Failed to read line");

        let option : u32 = match option.trim().parse() {
            Ok(num) => num,
            Err(_) => continue
        };
        if option == 1 || option == 2 || option == 3 || option == 4 {
            match option {
                1 => {
                    if let Some(sales_vector) = hashmap.get(&Function::Sales) {
                        println!("Sales Employees");
                        for name in sales_vector {
                            println!("{}", name);
                        }
                    }
                    break;
                }
                2 => {
                    if let Some(marketing_vector) = hashmap.get(&Function::Marketing){
                        println!("Marketing Employees");
                        for name in marketing_vector {
                            println!("{}", name);
                        }
                    }
                    break;
                }
                3 => {
                    if let Some(development_vector) = hashmap.get(&Function::Development){
                        println!("Development Employees");
                        for name in development_vector {
                            println!("{}", name);
                        }
                    }
                    break;
                }
                4 => {
                    if let Some(sales_vector) = hashmap.get(&Function::Sales) {
                        println!("Sales Employees");
                        for name in sales_vector {
                            println!("{}", name);
                        }
                    }

                    if let Some(marketing_vector) = hashmap.get(&Function::Marketing){
                        println!("\nMarketing Employees");
                        for name in marketing_vector {
                            println!("{}", name);
                        }
                    }

                    if let Some(development_vector) = hashmap.get(&Function::Development){
                        println!("\nDevelopment Employees");
                        for name in development_vector {
                            println!("{}", name);
                        }
                    }
                    break;
                }
                _ => continue
            }
        } 
    }
}
