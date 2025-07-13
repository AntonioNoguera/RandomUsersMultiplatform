import SwiftUI
import Shared

struct ContentView: View {
    
    @ObservedObject var viewModel: ViewModel
    
    var body: some View {
        VStack{
            Text("Users")
            List(viewModel.users, id: \.self) { user in
                
                //unwrapping
                if let name = user.name {
                    HStack {
                        AsyncImage(url: URL(string: user.picture!.thumbnail!))
                            .frame(width: 50, height: 50)
                            .clipShape(RoundedRectangle(cornerSize: CGSize(width: 10, height: 10)))
                        VStack(alignment: .leading, content: {
                            Text("\(name.first ?? "Firstname") \(name.last ?? "Lastname")")
                            Text(user.phone ?? "Phone") //Coalesce using '??' to provide a default when the optional value contains 'nil'
                        })
                    }
                }
            }
        }.onAppear{
            self.viewModel.observeDataFlow()
        }
    }
}

extension ContentView {
    
    @MainActor
    class ViewModel : ObservableObject {
        var homeRepository: HomeRepository = HomeRepository.init()
        
        @Published var users: [RandomUser] = []
        
        func observeDataFlow() {
            Task {
                do {
                    let users = try await homeRepository.getUsersSync()
                    await MainActor.run {
                        self.users = users
                    }
                } catch {
                    print("Error: \(error)")
                }
            }
        }
    }
}
    
    //struct ContentView_Previews: PreviewProvider {
    //    static var previews: some View {
    //        ContentView()
    //    }
    //}
