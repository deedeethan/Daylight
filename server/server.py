from twilio.rest import TwilioRestClient 

def parse_data (msg):
    result = msg.split('@$')
    print result
    result[0] = (int)(result[0])
    result[1] = (int)(result[1])
    result[2] = (result[2].lower()).encode('ascii', 'replace')
    result[3] = (int)(result[3])
    result[4] = ((result[4].encode('ascii', 'replace')),)
    print tuple(result)+(1,)
    return tuple(result)+(1,)
 

def main():
# put your own credentials here 
    ACCOUNT_SID = "ACb24a88bda202b6337947b3246dd8e4cd" 
    AUTH_TOKEN = "f29512cf75069ce85f7a148c5cebba78" 
     
    client = TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN) 
     
    messages = client.messages.list(To="+14125203163")

    unsorted_results = []
    unsorted_coords = []

    for m in messages:
        message = client.messages.get(m.sid)
        msg = message.body
        if (msg).startswith("Sent from your"):
            continue
        elif (msg).count("@$") < 4: continue
        else:
            result = parse_data(msg)
            if ((result[0], result[1])) in unsorted_coords:
                for x in unsorted_results:
                    x = list(x)
                    if x[0] == result[0] and x[1] == result[1]:
                        x[3] = result[3]+x[3]
                        x[5] = x[5]+1
                        x[4] = x[4]+result[4]
               
            else:
                unsorted_coords.append((result[0], result[1]))
                unsorted_results.append(result)
        
    sorted_results = sorted(unsorted_results)
    for x in sorted_results:
        x = list(x)
        x[3] = float(x[3])/float(x[5])
        x.pop()

    for x in sorted_results:
        print(str(x))

main()