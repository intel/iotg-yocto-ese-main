# hardknott go module issue due to new Go version
# New Go versions has Go modules support enabled by default and cause the Glide
# tool build to fail.
export GO111MODULE = "off"
