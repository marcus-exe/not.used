fn main() {
    use std::collections::HashMap;
    
    let mut scores = HashMap::new();

    scores.insert(String::from("Blue"), 10);
    scores.insert(String::from("Yellow"), 50);
    
    //read
    let team_name = String::from("Blue");
    let score = scores.get(&team_name).copied().unwrap_or(0);

    //loops
    for (key, value) in &scores {
        println!("{key}: {value}");
    }
    println!("");
    
    //we cannot insert reference to values in a hashmap
    
    //overwritting values
    scores.insert(String::from("Blue"), 10);
    scores.insert(String::from("Blue"), 25);
    println!("{:?}", scores);
    
    println!("");
    //add if not present
    scores.entry(String::from("Green")).or_insert(50);
    scores.entry(String::from("Blue")).or_insert(50);

    println!("{:?}", scores);
    
    //updating a value
    
    let text = "hello world wonderful world";
    let mut map = HashMap::new();
    for word in text.split_whitespace() {
        let count = map.entry(word).or_insert(0);
        *count += 1;
    }
    println!("{:?}", map);
    
    //By default, HashMap uses a hashing function called 
    //SipHash that can provide resistance to Denial of Service (DoS) attacks involving hash tables1.

}
