fn main() {
    
    let mut string_vec: Vec<String> = Vec::new();
    
    println!("");

    for i in 1..=12 {
        if i == 1 {
            string_vec.push(String::from("A partridge in a pear tree"));
            string_vec.push(String::from("On the first day of Christmas, my true love sent to me"));
        }
        if i == 2 {
            string_vec.push(String::from("On the second day of Christmas, my true love sent to me"));
            string_vec[i - 1] = String::from("Two turtle doves,");
            string_vec[0] = String::from("And partridge in a pear tree");
        }
        if i == 3 {
            string_vec.push(String::from("On the third day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Three French hens,");
        }

        if i == 4 {
            string_vec.push(String::from("On the fourth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Four calling birds,");
        }

        if i == 5 {
            string_vec.push(String::from("On the fifth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Five Golden Rings");
        }

        if i == 6 {
            string_vec.push(String::from("On the sixth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Six geese a-laying");
        }

        if i == 7 {
            string_vec.push(String::from("On the seventh day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Seven swans a-swimming");
        }
        if i == 8 {
            string_vec.push(String::from("On the eighth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Eight maids a-milking");
        }
        if i == 9 {
            string_vec.push(String::from("On the ninth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Nine ladies dancing");
        }
        if i == 10 {
            string_vec.push(String::from("On the tenth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Ten lords a-leaping");
        }
        if i == 11 {
            string_vec.push(String::from("On the eleventh day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Eleven pipers piping");
        }
        if i == 12 {
            string_vec.push(String::from("On the twelfth day of Christmas, my true love sent to me")); 
            string_vec[i - 1] = String::from("Twelve drummers drumming");
        }

        for line in string_vec.iter().rev(){
            println!("{}", line);
        }
        println!("");
    }
}


