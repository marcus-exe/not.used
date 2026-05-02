fn main() {
    //initializing
    let v1: Vec<i32> = Vec::new();

    //initializing and populating
    let v2 = vec![1, 2, 3];
    
    //adding
    let mut v3 = Vec::new();
    v3.push(5);
    v3.push(6);
    v3.push(7);
    v3.push(8);

    //Ways of accessing

    let v4 = vec![1, 2, 3, 4, 5];
    let third: &i32 = &v4[2];
    println!("The third element is {}", third);
    
    //this way can handle null pointer
    let third : Option<&i32> = v4.get(2);
    match third {
        Some(third) => println!("The third element is {}", third),
        None => println!("There is no third element"),
    }
    
    //when reference is valid, the problem is ownership
    //beware of pointer to mutable arrays

    let v5 = vec![100, 32, 57];
    for i in &v5 {
        println!("{i}");
    }
    
    let mut v6 = vec![100, 32, 57];
    for i in &mut v6 {
        *i += 50;
    }



}
