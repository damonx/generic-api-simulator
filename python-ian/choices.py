username = input('Please enter username to begin game: ')
choice = input('Welcome, {0}. Type "start" to start, {0}: '
.format(username))
inventory = ""
if choice == "start":
    print("You wake up in a dark forest with no idea of how you got there.")
    print("There is a sort of dreamy mist around you.")
    choice = input('To get up and investigate, type "investigate". To try to think how you got here type "think": ')
    if choice == "investigate":
        print("You get up.")
        print("Your footsteps echo as you walk through the forest, {0}. You suddenly spot a sword on the ground."
        .format(username))
        choice = input('To pick up the sword, type "pick up sword" and to continue walking type "continue walking": ')
        if choice == "pick up sword":
            inventory = "Sword"
            print('You pick up the sword. To view inventory type "inventory".')
            print("You then continue walking. A few minutes later, you come across a stranger.")
            print("He offers to take you to a village, where you can rest.")
            choice = input('To accept, type "accept". To decline, type "decline": ')
        if choice == "continue walking":
            inventory = ""
            print("You then continue walking. A few minutes later, you come across a stranger.")
            print("He offers to take you to a village, where you can rest.")
            choice = input('To accept, {0}, type "accept". To decline, type "decline": ')
        if choice == "inventory":
            print(inventory)
            choice = input('To accept, {0}, type "accept". To decline, type "decline": '
            .format(username))
        if choice == "decline":
            print('You politely but firmly say "No thanks, I\'m ok." and continue walking, {0}. You have won. Congrats!'
            .format(username))
        if choice == "accept":
            print('{0}, You say "Of course!" and follow the stranger.'
            .format(username))
            print('As soon as you reach the village, somebody carries a plate of delicious-looking food to you, {0}.'
            .format(username))
            choice = input('To accept the food, type "accept". To decline the food, type "decline": ')
            if choice == "accept":
                print("As soon as your lips touch the food, you feel dizzy and fall over.")
                print('Your last thought is, "The food was poisoned!" Try again?')
            if choice == "decline":
                print('You say, "No thanks, I\'m ok" and leave the village.')
                print("These days, your thoughts trouble you and you suspect it was because of the village and the stranger. Try again?")
    if choice == "think":
        print("You think hard and suddenly it comes back to you.")
        print("You were enjoying a day at the beach when you were sucked through a wormhole and plopped out over here.")
        print("No, it was when you were coding and you fell over inside the computer.")
        print("No, it was when you were reading a book and started living it out.")
        print("These thoughts cloud your brain until you can't bear it and then you get up.")
        print("Suddenly someone jumps out of a bush, draws his sword and kills you. Try again?")