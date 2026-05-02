use std::collections::HashMap;
fn main() {
    
    //creating the vector
    let mut vec: Vec<u32> = Vec::new();
    for i in 1..=5 {
        let counter = i;
        vec.push(counter*5);
    }
    for i in 1..=5{
        vec.push(4)
    }
    println!("Our vector is:");
    for i in &vec {
        print!(" {i}");
    }
    println!("");

    //testing the function
    let mode = return_mode(&vec);
    println!("The mode is: {}", mode);
}


fn return_mode(vec: &Vec<u32>) -> u32 {
    //creating a hashmap and populating based on a vector count
    let mut hashMap = HashMap::new();
    for value in vec {
        let count = hashMap.entry(value).or_insert(0);
        *count += 1
    }
    
    //with the hashmap in hands, we search for the biggest value and get it's key
    let mut temp1 = 0;
    for (key, value) in &hashMap{
        let temp2 = hashMap.get(key).copied().unwrap_or(0);
        if temp2 > temp1 {
            temp1 = temp2;
        }
    }
    // with the value in hands, we need to search for the key
    
    let mut mode = 0;
    for (key, value) in &hashMap {
        if value == &temp1 {
            mode = **key
        } 
    };
   mode 
}
