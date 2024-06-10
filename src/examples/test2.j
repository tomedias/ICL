let x = fn (x: int, y:bool, z:string) -> println z ; if y then println(x) else println(x+1) end; 5 : int end in
    let y = x(1, true, "hello") in
        println(y)
    end
end