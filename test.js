let valid = {
    messages: ["1", "2"]
}

valid.messages.push("3");

for (msg of valid.messages) {
    console.log(msg);
}

