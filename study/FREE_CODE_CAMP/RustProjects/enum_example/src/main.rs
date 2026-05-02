fn main() {
    enum IpAddrKind {
        V4,
        V6
    }
    enum IpAddr {
        V4(u8, u8, u8, u8),
        V6(String)
    }
    let home = IpAddr::V4(127,0,0,1);

    let home = IpAddr::V6(String::from("::1"));
    
    enum Message {
        Quit,
        Move {x : i32, y : i32},
        Write(String),
        ChangeColor(i32, i32, i32)
    }
    impl Message {
        fn call(&self){
            //method to define
        }
    }

    let m = Message::Write(String::from("Hello"));
    m.call();
    
    //Option: Some and None -> Null treating
    
    let some_number = Some(5);

    let some_char = Some('e');

    let absent_number : Option<i32> = None;




}
