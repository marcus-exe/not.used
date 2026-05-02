#[derive(Debug)]
struct Rectangle {
    width : u32,
    height : u32
}

impl Rectangle {
    fn area(&self) -> u32 {
        self.width * self.height
    }
    fn can_hold(&self, other: &Rectangle) -> bool {
        self.width > other.width && self.height > other.height
    }
    fn square(size: u32) -> Self {
        Self {
            width: size,
            height: size,
        }
    }
}



fn main() {
    
    let scale = 2;

    let rect_A = Rectangle {
        width: dbg!(30 * scale),
        height: 50,
    };

    
    let rect1 = Rectangle {
        width: 30,
        height: 50,
    };
    let rect2 = Rectangle {
        width: 10,
        height: 40,
    };
    let rect3 = Rectangle {
        width: 60,
        height: 45,
    };


    
    //println!("rect1 is {:#?}", rectA);
    println!("The area of the rectangle is {} square pixels.", rect_A.area());        
    dbg!(&rect_A);
    
    //associate methods
    println!("Can rect0 hold rect3? {}", rect1.can_hold(&rect3));
    println!("Can rect0 hold rect2? {}", rect1.can_hold(&rect2));

    //pretty much like a static method
    let square = Rectangle::square(3);
    dbg!(&square);

}
