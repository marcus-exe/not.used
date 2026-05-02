use std::io;

fn main() {

    loop {
        println!("Input word to be converted to pig-latin or type CTRL + C to exit");

        let mut string = String::new();
        io::stdin().read_line(&mut string)
            .expect("Failed to read line");
        let pig_latin = convert_to_pig_latin(&string);
        println!("{}", pig_latin);

    }




}

fn convert_to_pig_latin(string: &String) -> String{

    let substring = match &string[0..1]{
        "a" | "e" | "i" | "o" | "u" | "y" => &string[0..string.len()-1], 
        _ => &string[1..string.len()-1],
    };

    let prefix = match &string[0..1] {
         "a" | "e" | "i" | "o" | "u" | "y" => "h",
        _ => &string[0..1],
    };
    //let prefix = &string[0..1];
    let final_string = format!("{substring}-{prefix}ay");
    final_string
}
