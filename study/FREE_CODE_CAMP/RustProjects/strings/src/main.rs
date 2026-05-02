fn main() {
    //2 methods of declaring strings
    //first
    let mut s = String::new();
    
    //second
    let data = "initial contents";
    let s = data.to_string();
    // the method also works on a literal directly:
    let s = "initial contents".to_string();
    
    //utf-8 encoded
    let hello = String::from("السلام عليكم");
    let hello = String::from("Dobrý den");
    let hello = String::from("Hello");
    let hello = String::from("שָׁלוֹם");
    let hello = String::from("नमस्ते");
    let hello = String::from("こんにちは");
    let hello = String::from("안녕하세요");
    let hello = String::from("你好");
    let hello = String::from("Olá");
    let hello = String::from("Здравствуйте");
    let hello = String::from("Hola");
    
    //Updating Strings
    let mut s = String::from("foo");
    s.push_str("bar"); //the method does not take ownership of the string
    
    let mut s = String::from("lo");
    s.push('l'); //updating a single character
    
    //adding method 
    let s1 = String::from("Hello, "); //takes ownership of the first item
    let s2 = String::from("world!");
    let s3 = s1 + &s2; // note s1 has been moved here and can no longer be used
    
    let s1 = String::from("tic");
    let s2 = String::from("tac");
    let s3 = String::from("toe");

    //adding
    //let s = s1 + "-" + &s2 + "-" + &s3;
    //format method
    let s = format!("{s1}-{s2}-{s3}");
    
    let hello = String::from("Hola");

    for c in "Зд".chars() {
        println!("{c}");
    }
    for b in "Зд".bytes() {
        println!("{b}");
    }

}
